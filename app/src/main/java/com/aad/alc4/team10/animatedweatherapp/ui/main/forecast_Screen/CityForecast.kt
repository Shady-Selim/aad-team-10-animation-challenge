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
import kotlinx.android.synthetic.main.city_forecast_fragment.*

class CityForecast : Fragment() {

    companion object {
        fun newInstance() =
            CityForecast()
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

        initViewModel()


    }

    private fun initViewModel() = viewModel.run {
        loading.observe(this@CityForecast, Observer { onLoading(it) })
        error.observe(this@CityForecast, Observer { onError(it) })
        forecasts.observe(this@CityForecast, Observer { showForecasts(it) })

        btn_try_again.setOnClickListener { loadForecast("2172797") }

        loadForecast("2172797")
    }

    private fun showForecasts(forecasts: List<Forecast>) {

        tv_response.text = if (forecasts.isNullOrEmpty()) "" else forecasts.toString()
    }

    private fun onLoading(loading: Boolean) {
        pb_loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun onError(error: Boolean?) {
        if (error == true) {
            tv_response.visibility = View.GONE
            tv_error.visibility = View.VISIBLE
            btn_try_again.visibility = View.VISIBLE
        } else {

            tv_response.visibility = View.VISIBLE
            tv_error.visibility = View.GONE
            btn_try_again.visibility = View.GONE
        }

    }

}
