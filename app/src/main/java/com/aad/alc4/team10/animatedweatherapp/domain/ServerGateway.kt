package com.aad.alc4.team10.animatedweatherapp.ui.domain

import com.aad.alc4.team10.animatedweatherapp.BuildConfig
import com.aad.alc4.team10.animatedweatherapp.ui.ForecastsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val APP_ID_KEY = "appid"
private const val APP_ID_VALUE = BuildConfig.API_KEY
private const val OPEN_WEATHER_MAPS_URL = "https://api.openweathermap.org"

interface ServerApi {
    @GET("/data/2.5/forecast")
    suspend fun fetchForeCasts(
        @Query("id") cityId: String
        , @Query(APP_ID_KEY) appIdValue: String = APP_ID_VALUE
    ): ForecastsResponse
}

private val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_MAPS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
val retrofitApi: ServerApi by lazy { retrofit.create(ServerApi::class.java) }

