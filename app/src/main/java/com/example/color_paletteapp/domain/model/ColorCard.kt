package com.example.color_paletteapp.domain.model

import androidx.compose.ui.graphics.Color

data class ColorCard(
    val id: Int? = null,
    val hexCode: String,
    val name: String,
    val isSaved: Boolean = false,
    val composeColor: Color // Compose UI'da kullanılmak üzere dönüştürülmüş renk
)