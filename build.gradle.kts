/*
plugins {
    id("com.android.application") version "8.3.0" apply false // Veya en son sürüm
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}

plugins {
    id("com.android.application") version "8.6.0" apply false
    id("com.android.library") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
*/
plugins {// Android Gradle Plugin 8.6.0 (Java 17 gerektirir, önceki hataları çözer)
    id("com.android.application") version "8.6.0" apply false
    id("com.android.library") version "8.6.0" apply false

    // Kotlin Android Plugin (1.9.22 sürümü KSP ile tam uyumludur)
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

    // KSP Plugin (Room veritabanı için gereklidir)
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}
