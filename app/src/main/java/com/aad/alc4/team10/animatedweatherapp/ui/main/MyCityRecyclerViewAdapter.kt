package com.aad.alc4.team10.animatedweatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.City
import kotlinx.android.synthetic.main.fragment_city.view.*

class MyCityRecyclerViewAdapter(
    private val mCities: ArrayList<City?>,
    private val onClick: (City, View, TextView) -> Unit ?
) : RecyclerView.Adapter<MyCityRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val city = v.tag as City
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            onClick(city, v, v.tv_city_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_city,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mCities[position]?.let { holder.bindTo(it, mOnClickListener) }
    }

    override fun getItemCount(): Int = mCities.size

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {

        fun bindTo(city: City, mOnClickListener: View.OnClickListener) {
            mView.tv_city_name.text = city.name ?: " "
            mView.tag = city
            mView.setOnClickListener(mOnClickListener)
        }
    }
}