package com.aad.alc4.team10.animatedweatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.City
import com.aad.alc4.team10.animatedweatherapp.model.Country
import com.aad.alc4.team10.animatedweatherapp.model.getCountryPhotoRec
import com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.createPaletteSync
import kotlinx.android.synthetic.main.fragment_city_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CityFragment.OnListFragmentInteractionListener] interface.
 */
class CityFragment : Fragment() {

    private var columnCount = 1

    private var cities = arrayListOf<City?>()

    lateinit var country: Country

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            country = it.getParcelable<Country>(ARG_CITIES)
            cities.clear()
            cities.addAll(country.cities!!)
        }

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 500
                }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_city_list, container, false).apply {
        (activity as AppCompatActivity).supportActionBar?.hide()
        val photoTransitionName = "country_photo-${country.short}"
        val nameTransitionName = "country_name-${country.short}"
        ViewCompat.setTransitionName(img_country_photo, photoTransitionName)
        ViewCompat.setTransitionName(txt_country_name, nameTransitionName)

        with(country) {
            img_country_photo.setImageResource(getCountryPhotoRec())
            txt_country_name.text = name
        }

        // Set the adapter
        with(cities_recycler_view) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyCityRecyclerViewAdapter(cities) {
                findNavController().navigate(
                    CityFragmentDirections.actionCityFragmentToCityForecast(
                        it
                    )
                )
            }
        }
        country
            .getCountryPhotoRec()
            .let(resources::getDrawable).toBitmap()
            .let(::createPaletteSync)
            .getDominantColor(resources.getColor(R.color.off_white))
            .run(crd_country::setCardBackgroundColor)

    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_CITIES = "exportedcountry"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}