package com.example.color_paletteapp.data.remote

import com.example.color_paletteapp.data.remote.service.ColorApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.thecolorapi.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: ColorApiService by lazy {
        retrofit.create(ColorApiService::class.java)
    }
}