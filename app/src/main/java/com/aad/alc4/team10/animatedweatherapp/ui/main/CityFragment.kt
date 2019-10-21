package com.aad.alc4.team10.animatedweatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.model.City
import com.aad.alc4.team10.animatedweatherapp.model.Country
import com.aad.alc4.team10.animatedweatherapp.model.getCountryPhotoRec
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
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
                .apply {
                    duration = 700
                }
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            country = it.getParcelable<Country>(ARG_CITIES)
            cities.clear()
            cities.addAll(country.cities!!)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_city_list, container, false).apply {

        //activity?.title = country.name
        activity?.actionBar?.hide()
        img_country_photo.setImageResource(country.getCountryPhotoRec())
        txt_country_name.text = country.name
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