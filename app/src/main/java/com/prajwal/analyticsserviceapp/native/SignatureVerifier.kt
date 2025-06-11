package com.prajwal.analyticsserviceapp.native

object SignatureVerifier {
    init {
        System.loadLibrary("signature-verifier")
    }

    external fun nativeVerifySignature(signature: String): Boolean
}
