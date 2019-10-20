package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import Region
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aad.alc4.team10.animatedweatherapp.domain.FetchRegionUseCase

class RegionsViewModel(
    private val fetchRegionUseCase: FetchRegionUseCase=FetchRegionUseCase()
) : ViewModel() {
    val regionsLiveData = MutableLiveData<List<Region>?>()

fun getRegions(isConnected:Boolean) = fetchRegionUseCase
    .takeIf { regionsLiveData.value==null||regionsLiveData.value!!.isEmpty() }
    ?.run { invoke(isConnected,regionsLiveData) } ?: Unit

}

fun Activity.checkConnectivity(): Boolean =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .run { activeNetworkInfo != null && activeNetworkInfo.isConnected }
