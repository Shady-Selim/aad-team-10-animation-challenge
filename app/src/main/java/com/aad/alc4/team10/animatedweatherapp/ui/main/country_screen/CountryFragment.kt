package com.aad.alc4.team10.animatedweatherapp.ui.main.country_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.Region
import com.aad.alc4.team10.animatedweatherapp.model.getRegionPhoto
import com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.createPaletteSync
import kotlinx.android.synthetic.main.fragment_country_list.view.*

class CountryFragment : Fragment() {


    private var columnCount = 2

    lateinit var region: Region
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 700
                }

        region = arguments?.getParcelable<Region>("exportedRegion")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_country_list, container, false).apply {
        (activity as AppCompatActivity).supportActionBar?.show()

        ViewCompat.setTransitionName(img_region_photo, "region_photo${region.name}")
        img_region_photo.setImageResource(region.getRegionPhoto())
        region
            .also { activity?.title = it.name }
            .getRegionPhoto()
            .let(resources::getDrawable).toBitmap()
            .let(::createPaletteSync)
            .getDominantColor(resources.getColor(R.color.off_white))
            .run(::setBackgroundColor)


        // Set the adapter
        with(regions_recycler_view) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyCountryRecyclerViewAdapter(region.countries!!)
        }

    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CountryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
