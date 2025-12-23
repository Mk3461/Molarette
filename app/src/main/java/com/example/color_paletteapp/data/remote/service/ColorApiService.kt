package com.example.color_paletteapp.data.remote.service

import com.example.color_paletteapp.data.remote.model.ColorApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ColorApiService {


    @GET("random")
    suspend fun getRandomColor(): ColorApiModel


    @GET("id")
    suspend fun searchColorByHex(
        @Query("hex") hexCode: String
    ): ColorApiModel
}