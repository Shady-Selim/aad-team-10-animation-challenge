package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.Region
import com.aad.alc4.team10.animatedweatherapp.model.getRegionPhoto

fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

class RegionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    private val photo by lazy { mView.findViewById<ImageView>(R.id.region_photo_image_view) }
    private val nameTextView by lazy { mView.findViewById<TextView>(R.id.region_name_text_view) }

    private val card by lazy { mView.findViewById<CardView>(R.id.item_region_card_view) }

    fun bind(region: Region) = region
        .also { photo.setImageResource(it.getRegionPhoto()) }
        .apply { nameTextView.text = name }
        .also { handleOnClick(it) }
        .let { createPaletteSync(mView.context.resources.getDrawable(it.getRegionPhoto()).toBitmap()) }
        .run { card.setCardBackgroundColor(getDominantColor(mView.resources.getColor(R.color.off_white))) }

    private fun handleOnClick(region: Region) = with(itemView) {
        setOnClickListener { startCountriesScreen(region) }

    }


    private fun startCountriesScreen(region: Region) = RegionFragmentDirections
        .actionRegionFragmentToCountryFragment(region)
        .let { action ->
            "region_photo${region.name}"
                .also { ViewCompat.setTransitionName(photo, it) }
                .let { FragmentNavigatorExtras(photo to it) }
                .also { extras ->
                    itemView.findNavController()
                        .navigate(action.actionId, action.arguments, null, extras)
                }

        }
}

class MyRegionRecyclerViewAdapter(
    lifecycleOwner: LifecycleOwner,
    private val regions: MutableLiveData<List<Region>?>
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
        .let { holder.bind(it) }
}
