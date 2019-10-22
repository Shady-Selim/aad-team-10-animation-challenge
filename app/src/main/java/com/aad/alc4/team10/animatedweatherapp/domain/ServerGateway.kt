package com.aad.alc4.team10.animatedweatherapp.domain

import com.aad.alc4.team10.animatedweatherapp.BuildConfig.API_KEY
import com.aad.alc4.team10.animatedweatherapp.model.Region
import com.aad.alc4.team10.animatedweatherapp.ui.ForecastsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val APP_ID_KEY = "appid"
private const val APP_ID_VALUE = API_KEY
private const val OPEN_WEATHER_MAPS_URL = "https://api.openweathermap.org"
private const val REGIONS_API = "https://backendlessappcontent.com"

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


private val regionsRetrofitInstance by lazy {
    Retrofit.Builder()
        .baseUrl(REGIONS_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

interface RegionApi {
    @GET("/879DD966-7C96-7942-FF08-D16686100000/console/czlueqbvwqzndcxjqedplqtevzbfvomocxve/files/view/web/api")
    suspend fun fetchRegions(): List<Region>
}

val regionApi by lazy { regionsRetrofitInstance.create(RegionApi::class.java) }
