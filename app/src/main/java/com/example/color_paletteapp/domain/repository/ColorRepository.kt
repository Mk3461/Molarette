package com.example.color_paletteapp.domain.repository

import com.example.color_paletteapp.domain.model.ColorCard
import kotlinx.coroutines.flow.Flow

interface ColorRepository {


    suspend fun fetchRandomColor(): ColorCard


    suspend fun searchColorApi(query: String): ColorCard?


    fun getSavedColors(): Flow<List<ColorCard>>


    suspend fun saveColor(colorCard: ColorCard)


    suspend fun deleteColor(id: Int)
}