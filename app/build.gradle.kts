import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.hilt.android)
}

// Access the local.properties file
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

// Retrieve the API key from local.properties
val apiKey: String = localProperties.getProperty("API_KEY", "default_api_key_if_missing")

android {
    namespace = "com.adyen.android.assignment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.adyen.android.assignment"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "NASA_BASE_URL", "\"https://api.nasa.gov\"")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.google.android.material)
    implementation(libs.coil.compose)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    implementation(libs.androidx.fragment)
    implementation(libs.androidx.runtime.livedata)

    implementation(libs.hilt.android)
    ksp(libs.hilt.ksp)
    implementation(libs.hilt.navigation)

    implementation(libs.okhttp.interceptor)
    implementation(libs.moshi.kotlin)
    //implementation(libs.retrofit.moshi.converter)
    ksp(libs.moshi.ksp)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

