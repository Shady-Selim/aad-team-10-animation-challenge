package com.aad.alc4.team10.animatedweatherapp.domain

import Region
import androidx.lifecycle.MutableLiveData
import com.aad.alc4.team10.animatedweatherapp.ui.ForecastsResponse
import com.aad.alc4.team10.animatedweatherapp.ui.domain.ServerApi
import com.aad.alc4.team10.animatedweatherapp.ui.domain.regionApi
import com.aad.alc4.team10.animatedweatherapp.ui.domain.retrofitApi
import com.weather.useecasses.engine.getData
import retrofit2.Call

class ServerRepository(private val server: ServerApi = retrofitApi) {
    companion object {
        val instance = ServerRepository()
    }

    suspend fun getForecasts(cityId: String): ForecastsResponse = server.fetchForeCasts(cityId)

}

//for testing
interface BaseRegionsRepository {
    fun getRegions(): Call<List<Region>?>
}

val regionRepository by lazy { RegionRepository() }

class RegionRepository : BaseRegionsRepository {
    override fun getRegions() = regionApi.fetchRegions()
}


//UseCase
class FetchRegionUseCase(private val repository: BaseRegionsRepository = regionRepository) {
    operator fun invoke(isConnected: Boolean, result: MutableLiveData<List<Region>?>) = repository
        .takeIf { isConnected }
        ?.getRegions()
        ?.getData({ result.value = it }, { it.printStackTrace() })
}