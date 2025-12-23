package com.example.color_paletteapp.data.mapper

import com.example.color_paletteapp.data.local.entity.ColorEntity
import com.example.color_paletteapp.data.remote.model.ColorApiModel
import com.example.color_paletteapp.domain.model.ColorCard
import androidx.compose.ui.graphics.Color
import android.graphics.Color as AndroidColor // İsim çakışmasını önlemek için

object ColorMapper {

    private fun hexToComposeColor(hexCode: String): Color {
        // Compose Color nesnesi oluştururken Hex kodunu kullanır.
        return try {
            Color(AndroidColor.parseColor("#$hexCode"))
        } catch (e: IllegalArgumentException) {
            Color.Gray // Hata durumunda varsayılan renk
        }
    }

    // 1. API -> Domain Dönüşümü
    fun mapApiToDomain(apiModel: ColorApiModel): ColorCard {
        val hexCode = apiModel.hex.value.removePrefix("#") // # kaldırılır
        return ColorCard(
            hexCode = hexCode,
            name = apiModel.name.value,
            isSaved = false,
            composeColor = hexToComposeColor(hexCode)
        )
    }

    // 2. Domain -> Entity Dönüşümü (Kaydetmek İçin)
    fun mapDomainToEntity(domainModel: ColorCard): ColorEntity {
        return ColorEntity(
            id = domainModel.id ?: 0,
            hexCode = domainModel.hexCode,
            userDefinedName = domainModel.name,
            timestamp = System.currentTimeMillis()
        )
    }

    // 3. Entity -> Domain Dönüşümü (Veritabanından Çekmek İçin)
    fun mapEntityToDomain(entity: ColorEntity): ColorCard {
        return ColorCard(
            id = entity.id,
            hexCode = entity.hexCode,
            name = entity.userDefinedName ?: "Kaydedilen Renk",
            isSaved = true,
            composeColor = hexToComposeColor(entity.hexCode)
        )
    }
}