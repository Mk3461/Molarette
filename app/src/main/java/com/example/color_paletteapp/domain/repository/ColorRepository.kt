package com.example.color_paletteapp.domain.repository

import com.example.color_paletteapp.domain.model.ColorCard
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    // API'den rastgele renk çeker
    suspend fun fetchRandomColor(): ColorCard

    // API'den arama yapar (isteğe bağlı)
    suspend fun searchColorApi(query: String): ColorCard?

    // Veritabanından kaydedilmiş renkleri Flow ile dinler
    fun getSavedColors(): Flow<List<ColorCard>>

    // Veritabanına renk kaydeder
    suspend fun saveColor(colorCard: ColorCard)

    // Veritabanından renk siler
    suspend fun deleteColor(id: Int)
}