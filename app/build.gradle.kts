plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.plugin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("kapt")
}

android {
    namespace = "com.app.gweather"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.app.gweather"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey: String = (project.findProperty("OPENWEATHER_API_KEY") as? String) ?: ""
        buildConfigField("String", "OPENWEATHER_API_KEY", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Compose - UI toolkit for Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.coil)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.navigation.compose)
    implementation(libs.material3)
    implementation(libs.material3.window.size)
    implementation(libs.tooling.preview)
    implementation(libs.viewmodel.compose)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Lifecycle - ViewModel and LiveData
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Hilt - Dependency injection
    implementation(libs.hilt.android)
    implementation(libs.play.services.location)
    implementation(libs.ads.mobile.sdk)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.android.hilt.compiler)
    implementation(libs.hilt.work)

    // Retrofit2 and Okhttp
    implementation(libs.retrofit2.version)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.moshi)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Test Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}