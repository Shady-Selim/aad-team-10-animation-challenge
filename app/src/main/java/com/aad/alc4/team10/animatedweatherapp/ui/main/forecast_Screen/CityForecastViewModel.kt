package com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aad.alc4.team10.animatedweatherapp.domain.ServerRepository
import com.aad.alc4.team10.animatedweatherapp.ui.City
import com.aad.alc4.team10.animatedweatherapp.ui.Forecast
import com.weather.useecasses.engine.toMutableLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout


class CityForecastViewModel(
    private val _loading: MutableLiveData<Boolean> = false.toMutableLiveData(),
    private val _error: MutableLiveData<Boolean> = false.toMutableLiveData(),
    private val _forecasts: MutableLiveData<List<Forecast>> = listOf<Forecast>().toMutableLiveData(),
    private val _city: MutableLiveData<City> = MutableLiveData(),
    private val ropository: ServerRepository = ServerRepository.instance
) : ViewModel() {

    val loading: LiveData<Boolean>
        get() = _loading
    val forecasts: LiveData<List<Forecast>>
        get() = _forecasts
    val error: LiveData<Boolean>
        get() = _error
    val city: LiveData<City>
        get() = _city

    fun loadForecast(cityId: String) =
        viewModelScope.launch {
            if (loading.value == false) {
                _loading.postValue(true)
                try {
                    _error.postValue(false)
                    withTimeout(5000) {
                        val response = ropository.getForecasts(cityId)
                        response.apply {
                            _forecasts.postValue(forecasts)
                            _city.postValue(city)
                        }

                    }
                } catch (e: Exception) {
                    _error.postValue(true)
                    //handle error
                } finally {
                    _loading.postValue(false)
                }


            }

        }

}
