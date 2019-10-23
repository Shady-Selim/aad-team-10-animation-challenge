package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aad.alc4.team10.animatedweatherapp.domain.RegionRepository
import com.aad.alc4.team10.animatedweatherapp.model.Region
import com.weather.useecasses.engine.toMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class RegionsViewModel(
    private val regionRepository: RegionRepository = RegionRepository.instance,
    private val _loading: MutableLiveData<Boolean> = false.toMutableLiveData()
) : ViewModel() {
    val regionsLiveData = MutableLiveData<List<Region>?>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getRegions(isConnected: Boolean, onError: (Boolean) -> Unit) {
        regionsLiveData.value ?: viewModelScope.launch {
            if (!isConnected) {
                withContext(Dispatchers.Main) { onError(true) }
                return@launch
            }
            if (loading.value == false) {
                _loading.postValue(true)

                try {
                    withTimeout(5000) {
                        regionsLiveData.postValue(regionRepository.getRegions())
                    }
                } catch (e: Throwable) {
                    withContext(Dispatchers.Main) { onError(true) }
                } finally {
                    _loading.postValue(false)
                }

            }
        }


    }


}

fun Activity.checkConnectivity(): Boolean =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .run { activeNetworkInfo != null && activeNetworkInfo.isConnected }
