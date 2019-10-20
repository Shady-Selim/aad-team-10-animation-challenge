package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import Region
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aad.alc4.team10.animatedweatherapp.domain.FetchRegionUseCase

class RegionsViewModel(
    private val fetchRegionUseCase: FetchRegionUseCase=FetchRegionUseCase()
) : ViewModel() {
    val regionsLiveData = MutableLiveData<List<Region>?>()

fun getRegions(isConnected:Boolean) = fetchRegionUseCase(isConnected,regionsLiveData)
}