plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // <-- ДОБАВЬТЕ ЭТУ СТРОКУ
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.fitnessplusapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fitnessplusapp"
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
    // <-- ВАЖНО: ВКЛЮЧАЕМ VIEW BINDING -->
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Material
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Lifecycle
    val lifecycleVersion = "2.8.1" // Используем val вместо def
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    //Retrofit independencies
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //Logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.0")

    implementation("com.google.dagger:hilt-android:2.48") // <-- ДОБАВЬТЕ ЭТУ СТРОКУ
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0") // <-- ДОБАВЬТЕ ЭТУ СТРОКУ

    implementation("dagger.hilt:hilt-android-components:1.1.0") // Или новее

}
