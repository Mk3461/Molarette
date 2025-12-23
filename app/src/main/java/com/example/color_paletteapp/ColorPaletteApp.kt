// com.example.color_paletteapp/ColorPaletteApp.kt

package com.example.color_paletteapp

import android.app.Application
import androidx.room.Room // Room için gerekli
import com.example.color_paletteapp.data.local.AppDatabase // Veritabanı sınıfı
import com.example.color_paletteapp.data.local.dao.ColorDao // DAO sınıfı
import com.example.color_paletteapp.data.remote.RetrofitClient // Retrofit Client
import com.example.color_paletteapp.data.remote.service.ColorApiService // Retrofit Servisi
import com.example.color_paletteapp.data.repository.ColorRepositoryImpl
import com.example.color_paletteapp.domain.repository.ColorRepository // Arayüz

class ColorPaletteApp : Application() {

    // 1. DATABASE Tanımı (Room)
    // 🛑 Bu blok sınıfın direkt üyesi olmalıdır.
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "color_database"
        ).build()
    }

    // 2. DAO (Veri Erişim Nesnesi)
    private val colorDao: ColorDao by lazy { database.colorDao() }

    // 3. API Servisi
    private val colorApiService: ColorApiService by lazy { RetrofitClient.api }

    // 4. Repository (Uygulamanın Bağımlılığı)
    // 🛑 Hata aldığınız satır budur. Diğerleri doğruysa bu da düzelir.
    val colorRepository: ColorRepository by lazy {
        ColorRepositoryImpl(
            api = colorApiService,
            dao = colorDao
        )
    }
}