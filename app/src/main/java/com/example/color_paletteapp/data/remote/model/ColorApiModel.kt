package com.example.color_paletteapp.data.remote.model

import com.squareup.moshi.Json

// API'den gelen ana renk objesi
data class ColorApiModel(
    @field:Json(name = "hex")
    val hex: ColorHex,
    @field:Json(name = "name")
    val name: ColorName,
    @field:Json(name = "rgb")
    val rgb: ColorRgb
)

data class ColorHex(
    @field:Json(name = "value")
    val value: String
)

data class ColorName(
    @field:Json(name = "value")
    val value: String
)

data class ColorRgb(
    @field:Json(name = "value")
    val value: String
)