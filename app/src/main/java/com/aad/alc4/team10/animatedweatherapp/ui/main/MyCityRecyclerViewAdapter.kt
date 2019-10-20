package com.aad.alc4.team10.animatedweatherapp.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.City


import com.aad.alc4.team10.animatedweatherapp.ui.main.CityFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_city.view.*

class MyCityRecyclerViewAdapter(
    private val mCities: List<City>?,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyCityRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val city = v.tag as City
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(city)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = mCities?.get(position)
        holder.mTvCityName.text = city?.name ?: " "

        with(holder.mView) {
            tag = city
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mCities?.size ?: 0

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTvCityName: TextView = mView.tv_city_name

        override fun toString(): String {
            return super.toString() + " '" + mTvCityName.text + "'"
        }
    }
}
