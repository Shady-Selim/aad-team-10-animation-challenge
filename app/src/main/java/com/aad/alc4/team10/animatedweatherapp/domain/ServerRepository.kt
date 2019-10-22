package com.aad.alc4.team10.animatedweatherapp.domain

import com.aad.alc4.team10.animatedweatherapp.model.Region
import com.aad.alc4.team10.animatedweatherapp.ui.ForecastsResponse

class ServerRepository(private val server: ServerApi = retrofitApi) {
    companion object {
        val instance = ServerRepository()
    }

    suspend fun getForecasts(cityId: String): ForecastsResponse = server.fetchForeCasts(cityId)

}

//for testing
interface BaseRegionsRepository {
    suspend fun getRegions(): List<Region>
}

class RegionRepository : BaseRegionsRepository {
    companion object {
        val instance by lazy { RegionRepository() }
    }

    override suspend fun getRegions() = regionApi.fetchRegions()
}