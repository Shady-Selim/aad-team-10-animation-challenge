package com.aad.alc4.team10.animatedweatherapp.ui

import com.google.gson.annotations.SerializedName


data class ForecastsResponse(
    @SerializedName("city") val city: City?,
    @SerializedName("cnt") val count: Long?,
    @SerializedName("list") val forecasts: List<Forecast>?
)

data class City(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("coord") val coordinates: Coordinates?
)

data class Coordinates(
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lon") val longitude: Double?
)

data class Snow(@field:SerializedName("3h") val volumeForLastThreeHours: Double?)

data class Clouds(@field:SerializedName("all") val cloudiness: Long?)


data class Forecast(
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("dt") val date: Long?,
    @SerializedName("dt_txt") val dateText: String?,
    @SerializedName("main") val details: ForecastDetails?,
    @SerializedName("snow") val snow: Snow?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("wind") val wind: Wind?
)

data class Weather(
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("id") val id: Long?,
    @SerializedName("main") var state: String?
)

data class Wind(
    @SerializedName("deg") val degree: Double?,
    @SerializedName("speed") val speed: Double?
)

data class ForecastDetails(
    @SerializedName("grnd_level") val grandLevel: Double?,
    @SerializedName("humidity") val humidity: Long?,
    @SerializedName("pressure") val pressure: Double?,
    @SerializedName("sea_level") val seaLevel: Double?,
    @SerializedName("temp") val temperature: Double?,
    @SerializedName("temp_max") val maximumTemperature: Double?,
    @SerializedName("temp_min") val minimumTemperature: Double?
)
