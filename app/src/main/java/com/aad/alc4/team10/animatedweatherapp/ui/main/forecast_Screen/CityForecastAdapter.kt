package com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.ui.City
import com.aad.alc4.team10.animatedweatherapp.ui.Forecast
import com.aad.alc4.team10.animatedweatherapp.ui.Weather
import com.aad.alc4.team10.animatedweatherapp.ui.main.forecast_Screen.utils.ForecastUtils
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class CityForecastAdapter(
    private val forecastsLiveData: LiveData<List<Forecast>>,
    private val cityLiveData: LiveData<City>,
    owner: LifecycleOwner,
    private val mContext: Context,
    private val mClickHandler: ForecastAdapterOnClickHandler
) : RecyclerView.Adapter<CityForecastAdapter.CityForecastAdapterViewHolder>() {

    private lateinit var mWeathers: List<Weather>

    private val mUseTodayLayout: Boolean

    interface ForecastAdapterOnClickHandler {
        fun onClick(forecast: Forecast)
    }

    init {
        forecastsLiveData.observe(owner, androidx.lifecycle.Observer { notifyDataSetChanged() })
        cityLiveData.observe(owner, androidx.lifecycle.Observer { notifyDataSetChanged() })
        mUseTodayLayout = mContext.resources.getBoolean(R.bool.use_today_layout)

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): CityForecastAdapterViewHolder {

        val layoutId: Int

        when (viewType) {

            VIEW_TYPE_TODAY -> {
                layoutId = R.layout.list_item_forecast_today
            }

            VIEW_TYPE_FUTURE_DAY -> {
                layoutId = R.layout.list_item_forecast
            }

            else -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }

        val view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false)

        view.isFocusable = true

        return CityForecastAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityForecastAdapterViewHolder, position: Int) {
        mWeathers = forecastsLiveData.value!![position].weather!!
        val (_, date, dateText, forecastDetails) = getItemAtPosition(position)

        val weather = mWeathers[0]


        val weatherId = forecastsLiveData.value!![0].weather!![0].id!!.toInt()

        val viewType = getItemViewType(position)
        val weatherImageId: Int

        when (viewType) {

            VIEW_TYPE_TODAY -> {
                weatherImageId = ForecastUtils
                    .getLargeArtResourceIdForWeatherCondition(weatherId)
                val cityName = cityLiveData.value!!.name
                val countryName = cityLiveData.value!!.country

                holder.cityName.text = "$cityName , $countryName"

                Glide.with(mContext).load(weatherImageId)
                    .into(holder.iconView)
            }

            VIEW_TYPE_FUTURE_DAY -> {
                when(weather.icon){
                    "01d" -> Glide.with(mContext).load(mContext.resources.getDrawable(R.drawable.ic_clear))
                        .into(holder.iconView)
                    "01n" -> Glide.with(mContext).load(mContext.resources.getDrawable(R.drawable.ic_noun_clear_night))
                        .into(holder.iconView)
                    else -> Glide.with(mContext).load("https://www.openweathermap.org/img/wn/"+weather.icon+"@2x.png")
                        .into(holder.iconView)
                }
//                weatherImageId = ForecastUtils
//                    .getSmallArtResourceIdForWeatherIcon(weather.icon!!)

            }

            else -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }

        val weekday = checkIfToday(date!!)

        holder.dateView.text = weekday

        holder.descriptionView.text = weather.description


        val highString = ForecastUtils.formatTemperature(
            mContext,
            forecastDetails!!.maximumTemperature!! - 273.15
        )

        holder.highTempView.text = highString

        val lowInCelsius = forecastDetails.minimumTemperature!!

        val lowString = ForecastUtils.formatTemperature(mContext, lowInCelsius - 273.15)

        holder.lowTempView.text = lowString

        holder.dateTime.text = dateText

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        holder.iconView.animation = AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation)

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        holder.container?.animation = AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation)
    }

    override fun getItemCount(): Int {
        return forecastsLiveData.value?.size ?: 0
    }

    fun getItemAtPosition(position: Int): Forecast {
        return forecastsLiveData.value!![position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (mUseTodayLayout && position == 0) {
            VIEW_TYPE_TODAY
        } else {
            VIEW_TYPE_FUTURE_DAY
        }
    }

    inner class CityForecastAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val iconView: ImageView

        val dateView: TextView
        val descriptionView: TextView
        val highTempView: TextView
        val lowTempView: TextView
        val cityName: TextView
        val dateTime: TextView
        var container : ConstraintLayout? = null

        init {

            iconView = view.findViewById<View>(R.id.iv_forecast_icon) as ImageView
            dateView = view.findViewById<View>(R.id.tv_forecast_date) as TextView
            descriptionView = view.findViewById<View>(R.id.tv_forecast_description) as TextView
            highTempView = view.findViewById<View>(R.id.tv_high_temperature) as TextView
            lowTempView = view.findViewById<View>(R.id.tv_low_temperature) as TextView
            cityName = view.findViewById<View>(R.id.tv_city_name) as TextView
            dateTime = view.findViewById<View>(R.id.tv_date_time_id) as TextView
            container = view.findViewById<View>(R.id.container) as? ConstraintLayout
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            mClickHandler.onClick(getItemAtPosition(adapterPosition))
        }
    }

    private fun checkIfToday(timeStamp: Long): String {
        val epochInMillis = timeStamp * 1000
        val now = Calendar.getInstance()
        val timeToCheck = Calendar.getInstance()
        timeToCheck.timeInMillis = epochInMillis
        if (now.get(Calendar.DAY_OF_YEAR) == timeToCheck.get(Calendar.DAY_OF_YEAR)) {
            return "Today"
        } else {
            val sd = SimpleDateFormat("EEEE")
            val dateFormat = java.util.Date(epochInMillis)
            return sd.format(dateFormat)
        }
    }

    companion object {

        private val VIEW_TYPE_TODAY = 0
        private val VIEW_TYPE_FUTURE_DAY = 1
    }
}