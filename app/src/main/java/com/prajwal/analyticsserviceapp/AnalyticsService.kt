package com.prajwal.analyticsserviceapp

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.prajwal.analyticsservice.AnalyticsData
import com.prajwal.analyticsservice.IAnalyticsService
import com.prajwal.analyticsserviceapp.native.SignatureVerifier

//Expected Deliverables
//2. AnalyticsService implementation with mocked metrics.
class AnalyticsService : Service() {

    private val binder = object : IAnalyticsService.Stub() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun getCurrentStats(): AnalyticsData {
            enforceCallingPermission()
            return generateMockedMetrics()
        }
    }

    //Expected Deliverables
    //4. Security setup using permission protection and signature check.
    //This can be moved to repo layer
    @RequiresApi(Build.VERSION_CODES.P)
    private fun enforceCallingPermission() {
        val requiredPermission = BuildConfig.PERMISSION_ANALYTICS

        // 1. Check if the caller has the required permission
        if (checkCallingPermission(requiredPermission) != PackageManager.PERMISSION_GRANTED) {
            throw SecurityException("Unauthorized access: permission denied.")
        }

        // 2. Check if the calling app's signature matches
        val signatureDigest = getCallerSignature()
            ?: throw SecurityException("Signature not found.")


        val isCallerTrusted = SignatureVerifier.nativeVerifySignature(signatureDigest)
        if (!isCallerTrusted) throw SecurityException("Invalid signature.")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getCallerSignature(): String? {
        val signatures = packageManager.getPackageInfo(
            "com.prajwal.analyticsserviceapp",
            PackageManager.GET_SIGNING_CERTIFICATES
        )
            .signingInfo
            ?.apkContentsSigners
        Log.d("Signature", signatures?.get(0)?.toCharsString() ?: "No signature found")
        return signatures?.get(0)?.toCharsString()
    }

    //Can be moved to repo layer
    //Mocked Metrics
    private fun generateMockedMetrics() = AnalyticsData(
        batteryLevel = 85,
        cpuUsage = 23.4f,
        memoryUsageMB = 512
    )

    override fun onBind(intent: Intent?): IBinder = binder
}
