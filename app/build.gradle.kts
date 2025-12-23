// app/build.gradle.kts (Module Level) - Düzeltilmiş Sürüm
/*
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.color_paletteapp" // KESİN PAKET ADI
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.color_paletteapp" // KESİN PAKET ADI
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures { compose = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }

    // 🛑 EKLENEN KISIM: Compose Compiler Sürümünü Kotlin 1.9.22'ye göre ayarlıyoruz.
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // <-- Bu satır çakışmayı çözer
    }
}

dependencies {
    // 1. CORE & COMPOSE
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    // 2. COROUTINES & VIEWMODEL
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // 3. NAVIGATION
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // 4. API (Retrofit & Moshi)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // 5. DATABASE (Room)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.compose.material3.android)
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.compose.material:material-icons-extended")
    // 6. TESTING
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

}
*/
plugins {    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // KSP sürümünü burada belirtmemize gerek yok, çünkü Project level build.gradle'da belirttik.
    // Ancak garanti olması için buraya sürümüyle yazıyorum:
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

android {
    namespace = "com.example.color_paletteapp"
    compileSdk = 34 // 36 henüz stabil olmayabilir, 34 (Android 14) en güvenlisidir.

    defaultConfig {
        applicationId = "com.example.color_paletteapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17 // AGP 8.x genelde Java 17 ister
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // Kotlin 1.9.22 ile uyumlu olan sürüm:
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- 1. CORE & COMPOSE ---
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // BOM (Bill of Materials) - Tüm Compose sürümlerini bu yönetir.
    // Çakışmayı önlemek için TEK BİR BOM kullanıyoruz ve en güncelini seçiyoruz (2024.06.00).
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material 3 (BOM tarafından yönetiliyor, sürüm yazmıyoruz)
    implementation("androidx.compose.material3:material3")

    // Extended Icons (AutoFixHigh vb. için)
    implementation("androidx.compose.material:material-icons-extended")

    // --- 2. NAVIGATION & LIFECYCLE ---
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // --- 3. API (RETROFIT & MOSHI) ---
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // --- 4. DATABASE (ROOM) ---
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion") // kapt yerine ksp kullanıyoruz

    // --- 5. TEST ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Test için de aynı BOM'u kullanıyoruz
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debug araçları
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //logo için
    implementation("androidx.core:core-splashscreen:1.0.1")
}
