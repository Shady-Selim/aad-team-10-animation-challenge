package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import com.aad.alc4.team10.animatedweatherapp.model.Region
import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import com.aad.alc4.team10.animatedweatherapp.R

fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

class RegionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    private val photo by lazy { mView.findViewById<ImageView>(R.id.region_photo_image_view) }
    private val nameTextView by lazy { mView.findViewById<TextView>(R.id.region_name_text_view) }

    private val card by lazy { mView.findViewById<CardView>(R.id.item_region_card_view) }

    fun bind(region: Region) = region
        .also { photo.setImageResource(setRegionPhoto(it)) }
        .apply { nameTextView.text = name }
        .let { createPaletteSync(mView.context.resources.getDrawable(setRegionPhoto(it)).toBitmap()) }
        .run { card.setCardBackgroundColor(getDominantColor(mView.resources.getColor(R.color.off_white))) }

    private fun setRegionPhoto(region: Region): Int = when (region.name) {
        "MENA" -> R.drawable.mena
        "Africa" -> R.drawable.africa
        "Asia" -> R.drawable.asia
        "Europe" -> R.drawable.europe
        else -> R.drawable.earth
    }
}

class MyRegionRecyclerViewAdapter(
    lifecycleOwner: LifecycleOwner,
    private val regions: MutableLiveData<List<Region>?>,
    private val onClick: (Region) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    init {
        regions.observe(lifecycleOwner, Observer { notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent
        .let { LayoutInflater.from(it.context).inflate(R.layout.item_region, it, false) }
        .let { RegionViewHolder(it) }

    override fun getItemCount() = regions.value?.size ?: 0

    override fun onBindViewHolder(
        holder: RegionViewHolder,
        position: Int
    ) = regions.value!![position]
        .also { holder.bind(it) }
        .let { region -> holder.mView.setOnClickListener { onClick(region) } }
}