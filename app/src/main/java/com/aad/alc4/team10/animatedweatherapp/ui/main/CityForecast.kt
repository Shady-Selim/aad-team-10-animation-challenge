package com.aad.alc4.team10.animatedweatherapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen.CityForecastViewModel
import com.aad.alc4.team10.animatedweatherapp.ui.main.model.Forecast

class CityForecast : Fragment(), CityForecastAdapter.ForecastAdapterOnClickHandler {
    override fun onClick(forecast: Forecast) {

    }

    companion object {
        fun newInstance() = CityForecast()
    }

    lateinit var recyclerView : RecyclerView

    private lateinit var viewModel: CityForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.city_forecast_fragment, container, false)
        activity?.actionBar?.hide()

        var adapter : CityForecastAdapter = CityForecastAdapter(activity!!.applicationContext,this)

        recyclerView = view.findViewById(R.id.rv_city_forecasts_list)
        recyclerView.adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CityForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }



}
