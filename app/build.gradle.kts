plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.animallovers.petfinder"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.animallovers.petfinder"
        minSdk = 26
        targetSdk = 35
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
    implementation(libs.androidx.ui.text.google.fonts)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ConstraintLayout
    implementation(libs.androidx.constraintlayout.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Retrofit
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)


    // glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // LiveData
    implementation(libs.androidx.runtime.livedata)


    //room
    implementation(libs.androidx.room.runtime)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Misc.
    implementation(libs.coil.compose)

    // Serializable
    implementation(libs.kotlinx.serialization.json)

    // mock
    testImplementation(libs.mockk)

    // test
    testImplementation(libs.kotlinx.coroutines.test)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Splash Api
    implementation(libs.androidx.core.splashscreen)

    // Pager and Indicators - Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Window Sizes
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")

    // Testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("app.cash.turbine:turbine:1.0.0")

}