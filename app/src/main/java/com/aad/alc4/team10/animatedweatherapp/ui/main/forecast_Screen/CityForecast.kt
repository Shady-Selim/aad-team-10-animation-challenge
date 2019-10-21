package com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.core.showAnimator
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
    private val errorObjectAnimator by lazy { img_error.showAnimator() }

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
        loadForecast(CAIRO_ID, ::onError)

    }

    private fun onLoading(loading: Boolean) {
        pb_loading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun onError(error: Boolean?) = if (error == true) {
        //handel error
        showErrorImage()
        showSnackbar()

    } else
        hideErrorImage()

    private fun showErrorImage(): Unit = with(img_error) {
        scaleX = 0f
        scaleY = 0f
        visibility = View.VISIBLE
        animate().apply {
            cancel()
            duration = 500
            scaleX(1f)
            scaleY(1f)
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    errorObjectAnimator.start()
                }
            }
            )
            start()
        }
    }

    private fun hideErrorImage(): Unit = with(img_error) {
        scaleX = 1f
        scaleY = 1f
        animate().apply {
            cancel()
            duration = 500
            scaleX(0f)
            scaleY(0f)
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    errorObjectAnimator.doOnCancel { visibility = View.GONE }
                    errorObjectAnimator.cancel()

                }
            })
            start()
        }
    }

    private fun showSnackbar() {
        Snackbar
            .make(rv_city_forecasts_list, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.try_again)) {
                onError(false)
                viewModel.loadForecast(CAIRO_ID, ::onError)
            }
            .show()
    }

    override fun onClick(forecast: Forecast) {}

}