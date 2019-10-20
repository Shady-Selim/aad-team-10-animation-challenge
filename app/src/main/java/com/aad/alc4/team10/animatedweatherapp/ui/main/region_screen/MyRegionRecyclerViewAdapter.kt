package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import Region
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aad.alc4.team10.animatedweatherapp.R

class RegionViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    private val mIdView by lazy { mView.findViewById<TextView>(R.id.item_number) }
    private val mContentView by lazy { mView.findViewById<TextView>(R.id.content) }

    @SuppressLint("SetTextI18n")
    fun bind(region: Region) {
        mIdView.text = "show"
        mContentView.text = region.name
    }
}

class MyRegionRecyclerViewAdapter(
    lifecycleOwner: LifecycleOwner,
    private val regions: MutableLiveData<List<Region>>,
    private val onClick: (Region) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    init {
        regions.observe(lifecycleOwner, Observer { notifyDataSetChanged() })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = parent
        .let { LayoutInflater.from(it.context).inflate(R.layout.fragment_region, it, false) }
        .let { RegionViewHolder(it) }

    override fun getItemCount() = regions.value?.size ?: 0

    override fun onBindViewHolder(
        holder: RegionViewHolder,
        position: Int
    ) = regions.value!![position]
        .also { holder.bind(it) }
        .let { region -> holder.mView.setOnClickListener { onClick(region) } }
}