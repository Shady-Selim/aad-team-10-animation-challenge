package com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.ui.Forecast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.city_forecast_fragment.*
import kotlinx.android.synthetic.main.city_forecast_fragment.view.*

const val CAIRO_ID = "360630"

class CityForecast : Fragment(), CityForecastAdapter.ForecastAdapterOnClickHandler {
    companion object {

        fun newInstance() =
            CityForecast()
    }

    private val viewModel: CityForecastViewModel by lazy {
        ViewModelProviders.of(this).get(CityForecastViewModel::class.java)
    }

    private val forecastAdapter by lazy {
        CityForecastAdapter(
            forecastsLiveData = viewModel.forecasts,
            cityLiveData = viewModel.city,
            owner = this,
            mContext = activity!!.applicationContext,
            mClickHandler = this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.city_forecast_fragment, container, false)
        .apply {
            activity?.actionBar?.hide()
            rv_city_forecasts_list.adapter = forecastAdapter

        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()


    }

    private fun initViewModel() = viewModel.run {
        loading.observe(this@CityForecast, Observer { onLoading(it) })
        error.observe(this@CityForecast, Observer { onError(it) })
        loadForecast(CAIRO_ID)

    }

    private fun onLoading(loading: Boolean) {
        pb_loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun onError(error: Boolean?) {
        if (error == true) {
            //handel error
            rv_city_forecasts_list.visibility = View.GONE
            showSnackbar()

        } else {
            //return to normal state
            rv_city_forecasts_list.visibility = View.VISIBLE

        }

    }

    private fun showSnackbar() {
        Snackbar
            .make(rv_city_forecasts_list, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.try_again)) { viewModel.loadForecast(CAIRO_ID) }
            .show()
    }

    override fun onClick(forecast: Forecast) {}

}
