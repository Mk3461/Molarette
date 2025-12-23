package com.example.color_paletteapp.data.repository

import com.example.color_paletteapp.data.local.dao.ColorDao
import com.example.color_paletteapp.data.remote.service.ColorApiService
import com.example.color_paletteapp.data.mapper.ColorMapper
import com.example.color_paletteapp.domain.model.ColorCard
import com.example.color_paletteapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ColorRepositoryImpl(
    private val api: ColorApiService,
    private val dao: ColorDao
) : ColorRepository {

    override suspend fun fetchRandomColor(): ColorCard {
        val apiModel = api.getRandomColor()
        return ColorMapper.mapApiToDomain(apiModel)
    }

    override suspend fun searchColorApi(query: String): ColorCard? {
        val cleanedQuery = query.removePrefix("#")
        return try {
            val apiModel = api.searchColorByHex(cleanedQuery)
            ColorMapper.mapApiToDomain(apiModel)
        } catch (e: Exception) {
            null // API'den renk bulunamazsa null döner
        }
    }

    override fun getSavedColors(): Flow<List<ColorCard>> {
        // DAO'dan gelen Entity listesini Flow'da iken Domain modeline dönüştürür.
        return dao.getAllColors().map { entityList ->
            entityList.map { entity ->
                ColorMapper.mapEntityToDomain(entity)
            }
        }
    }

    override suspend fun saveColor(colorCard: ColorCard) {
        val entity = ColorMapper.mapDomainToEntity(colorCard)
        dao.insertColor(entity)
    }

    override suspend fun deleteColor(id: Int) {
        dao.deleteColor(id)
    }
}