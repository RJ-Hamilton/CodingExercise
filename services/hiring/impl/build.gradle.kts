plugins {
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("com.android.library")
}

android {
    namespace = "com.hamilton.services.hiring.impl"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
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
}

dependencies {
    implementation(project(":services:hiring:api"))

    implementation(libs.kotlin.serialization.json)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.kotlin.serialization)
    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.okhttp3.logging)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
}