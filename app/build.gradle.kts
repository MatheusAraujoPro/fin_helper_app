import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sql.delight)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
}

val keystoreFile = project.rootProject.file("local.properties")
val properties = Properties()
properties.load(keystoreFile.inputStream())

android {
    namespace = "com.example.fin_helper_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fin_helper_app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        register("release") {
            storeFile = File(properties.getProperty("keystorelocation") ?: "")
            storePassword = properties.getProperty("keystorepass")
            keyAlias = properties.getProperty("keystorealias")
            keyPassword = properties.getProperty("keypassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

sqldelight {
    databases {
        create("Transactions") {
            packageName.set("data.sqldelight.database")
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    runtimeOnly(libs.androidx.material.icons.extended)
    implementation(libs.splash.screen)

    implementation("app.cash.sqldelight:android-driver:2.0.2")

    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
}