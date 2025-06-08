plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
apply(plugin = "kotlin-parcelize")

android {
    namespace = "com.prajwal.analyticsserviceapp"
    compileSdk = 35
    buildFeatures {
        aidl = true
        buildConfig = true
    }
    signingConfigs {
        getByName("debug").apply {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            val storeFilePath = System.getenv("RELEASE_STORE_FILE") ?: project.properties["RELEASE_STORE_FILE"] as String
            storeFile = file(storeFilePath)

            storePassword = System.getenv("RELEASE_STORE_PASSWORD") ?: project.properties["RELEASE_STORE_PASSWORD"] as String
            keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: project.properties["RELEASE_KEY_ALIAS"] as String
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: project.properties["RELEASE_KEY_PASSWORD"] as String
        }

    }

    defaultConfig {
        applicationId = "com.prajwal.analyticsserviceapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            buildConfigField("String", "PERMISSION_ANALYTICS", "\"com.prajwal.analyticsserviceapp.PERMISSION_ANALYTICS\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            buildConfigField("String", "PERMISSION_ANALYTICS", "\"com.prajwal.analyticsserviceapp.PERMISSION_ANALYTICS\"")
            signingConfig = signingConfigs.getByName("debug")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    sourceSets["main"].aidl.srcDirs("src/main/aidl")
}

dependencies {
    implementation(libs.androidx.core.ktx)
}