package com.example.color_paletteapp.data.remote.service

import com.example.color_paletteapp.data.remote.model.ColorApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ColorApiService {

    // Rastgele renk çeker. (Base URL: http://www.thecolorapi.com/id?)
    @GET("random")
    suspend fun getRandomColor(): ColorApiModel

    // Hex koduna göre renk arar. (Örn: /id?hex=FF5733)
    @GET("id")
    suspend fun searchColorByHex(
        @Query("hex") hexCode: String
    ): ColorApiModel
}