package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import Region
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aad.alc4.team10.animatedweatherapp.R

class RegionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    private val photo by lazy { mView.findViewById<ImageView>(R.id.region_photo_image_view) }
    private val nameTextView by lazy { mView.findViewById<TextView>(R.id.region_name_text_view) }

    fun bind(region: Region) = region
        .also { photo.setImageResource(setRegionPhoto(it)) }
        .run { nameTextView.text = name }

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