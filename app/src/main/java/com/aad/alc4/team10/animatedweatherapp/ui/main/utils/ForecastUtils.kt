package com.aad.alc4.team10.animatedweatherapp.ui.main.utils

import android.content.Context

import com.aad.alc4.team10.animatedweatherapp.R


object ForecastUtils {

    fun formatTemperature(context: Context, temperature: Double): String {
        var temperature = temperature

        val temperatureFormatResourceId = R.string.format_temperature

        return String.format(context.getString(temperatureFormatResourceId), temperature)
    }

    fun getSmallArtResourceIdForWeatherCondition(weatherId: Int): Int {

        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain
        } else if (weatherId == 511) {
            return R.drawable.ic_snow
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm
        } else if (weatherId == 800) {
            return R.drawable.ic_clear
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.ic_storm
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.ic_storm
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.ic_clear
        }

        return R.drawable.ic_storm
    }

    fun getSmallArtResourceIdForWeatherIcon(weatherIcon: String): Int {

        when(weatherIcon){
            "01d" ->  return R.drawable.ic_clear
            "01n" ->  return R.drawable.ic_clear
            "02d" ->  return R.drawable.ic_light_clouds
            "02n" ->  return R.drawable.ic_light_clouds
            "03d" ->  return R.drawable.ic_cloudy
            "03n" ->  return R.drawable.ic_cloudy
            "04d" ->  return R.drawable.ic_fog
            "04n" ->  return R.drawable.ic_fog
            "09d" ->  return R.drawable.ic_rain
            "09n" ->  return R.drawable.ic_rain
            "10d" ->  return R.drawable.ic_light_rain
            "10n" ->  return R.drawable.ic_light_rain
            "11d" ->  return R.drawable.ic_storm
            "11n" ->  return R.drawable.ic_storm
            "13d" ->  return R.drawable.ic_snow
            "13n" ->  return R.drawable.ic_snow
            "50d" ->  return R.drawable.ic_fog
            "50n" ->  return R.drawable.ic_fog
            else ->  return R.drawable.ic_storm
        }
    }


    fun getLargeArtResourceIdForWeatherCondition(weatherId: Int): Int {

        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain
        } else if (weatherId == 511) {
            return R.drawable.art_snow
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.art_storm
        } else if (weatherId == 800) {
            return R.drawable.art_clear
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.art_storm
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.art_storm
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.art_clear
        }

        return R.drawable.art_storm
    }
}