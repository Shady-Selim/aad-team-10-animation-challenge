package com.aad.alc4.team10.animatedweatherapp.domain

import com.aad.alc4.team10.animatedweatherapp.ui.ForecastsResponse
import com.aad.alc4.team10.animatedweatherapp.ui.domain.ServerApi
import com.aad.alc4.team10.animatedweatherapp.ui.domain.retrofitApi

class ServerRepository(private val server: ServerApi = retrofitApi) {
    companion object {
        val instance = ServerRepository()
    }

    suspend fun getForecasts(cityId: String): ForecastsResponse = server.fetchForeCasts(cityId)

}