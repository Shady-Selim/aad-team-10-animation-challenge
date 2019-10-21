package com.aad.alc4.team10.animatedweatherapp.ui.main.country_screen

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
            tag = item
            setOnClickListener { onclick.onClick(position) }
        }
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

        private fun setCountryPhoto(region: Country): Int = when (region.name) {
            "ARE" -> R.drawable.are
            "CHN" -> R.drawable.chn
            "DEU" -> R.drawable.deu
            "EGY" -> R.drawable.egy
            "FIN" -> R.drawable.fin
            "GBR" -> R.drawable.gbr
            "IND" -> R.drawable.ind
            "NGA" -> R.drawable.nga
            "PAK" -> R.drawable.pak
            "RWA" -> R.drawable.rwa
            "SAF" -> R.drawable.saf
            "SAU" -> R.drawable.sau
            else -> R.drawable.earth
        }
    }
}