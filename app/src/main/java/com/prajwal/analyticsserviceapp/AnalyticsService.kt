package com.prajwal.analyticsserviceapp

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import com.prajwal.analyticsservice.AnalyticsData
import com.prajwal.analyticsservice.IAnalyticsService

//Expected Deliverables
//2. AnalyticsService implementation with mocked metrics.
class AnalyticsService : Service() {

    private val binder = object : IAnalyticsService.Stub() {
        override fun getCurrentStats(): AnalyticsData {
            enforceCallingPermission()
            return generateMockedMetrics()
        }
    }

    //Expected Deliverables
    //4. Security setup using permission protection and signature check.
    private fun enforceCallingPermission() {
        if (checkCallingPermission(BuildConfig.PERMISSION_ANALYTICS) != PackageManager.PERMISSION_GRANTED) {
            throw SecurityException("Unauthorized access.")
        }
    }
    //Mocked Metrics
    private fun generateMockedMetrics() = AnalyticsData(
        batteryLevel = 85,
        cpuUsage = 23.4f,
        memoryUsageMB = 512
    )

    override fun onBind(intent: Intent?): IBinder = binder
}
