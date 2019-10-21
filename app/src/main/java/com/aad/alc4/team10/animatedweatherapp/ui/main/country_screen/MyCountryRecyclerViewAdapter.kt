package com.aad.alc4.team10.animatedweatherapp.ui.main.country_screen

import android.system.Os.bind
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.Country


import com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.createPaletteSync

class MyCountryRecyclerViewAdapter(
    private val mValues: List<Country>,
    private val onclick: OnCountryClicked
) : RecyclerView.Adapter<MyCountryRecyclerViewAdapter.CountryViewHolder>() {

    interface OnCountryClicked {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = mValues[position]


        with(holder.mView) {
            setOnClickListener { onclick.onClick(position) }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = mValues.size

    inner class CountryViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val photo by lazy { mView.findViewById<ImageView>(R.id.country_photo_image_view) }
        private val nameTextView by lazy { mView.findViewById<TextView>(R.id.country_name_text_view) }

        private val card by lazy { mView.findViewById<CardView>(R.id.item_country_card_view) }

        fun bind(co: Country) = co
            .also { photo.setImageResource(setCountryPhoto(it)) }
            .apply { nameTextView.text = name }
            .let { createPaletteSync(mView.context.resources.getDrawable(setCountryPhoto(it)).toBitmap()) }
            .run { card.setCardBackgroundColor(getDominantColor(mView.resources.getColor(R.color.off_white))) }

        private fun setCountryPhoto(country: Country): Int = when (country.short) {
            "are" -> R.drawable.are
            "chn" -> R.drawable.chn
            "deu" -> R.drawable.deu
            "egy" -> R.drawable.egy
            "fin" -> R.drawable.fin
            "gbr" -> R.drawable.gbr
            "ind" -> R.drawable.ind
            "nga" -> R.drawable.nga
            "pak" -> R.drawable.pak
            "rwa" -> R.drawable.rwa
            "saf" -> R.drawable.saf
            "sau" -> R.drawable.sau
            else -> R.drawable.earth
        }
    }
}