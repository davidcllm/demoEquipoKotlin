plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "david.ceballos.demo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "david.ceballos.helloworld"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Librería principal de Media3 para ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    // UI components (PlayerView para XML)
    implementation("androidx.media3:media3-ui:1.5.1")
    // Soporte para formatos comunes (Dash, HLS, etc.)
    implementation("androidx.media3:media3-common:1.5.1")
    implementation("com.google.android.material:material:1.x.x")
    //Soporte de FaceID
    implementation("androidx.biometric:biometric-compose:1.4.0-alpha05")

    // WEB SERVICES
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.13.2")
    //Soporte para el splash
    //implementation(libs.dotlottie.android)
    implementation("com.airbnb.android:lottie:6.4.1")
}