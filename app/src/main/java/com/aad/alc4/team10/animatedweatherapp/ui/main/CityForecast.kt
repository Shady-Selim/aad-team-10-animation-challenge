package com.aad.alc4.team10.animatedweatherapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aad.alc4.team10.animatedweatherapp.R

class CityForecast : Fragment() {

    companion object {
        fun newInstance() = CityForecast()
    }

    private lateinit var viewModel: CityForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CityForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
