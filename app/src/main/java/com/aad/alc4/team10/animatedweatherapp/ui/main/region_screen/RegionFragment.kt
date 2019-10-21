package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import com.aad.alc4.team10.animatedweatherapp.model.Region
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aad.alc4.team10.animatedweatherapp.R

import com.aad.alc4.team10.animatedweatherapp.ui.main.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_region_list.*

class RegionFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[RegionsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_region_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRegions(activity!!.checkConnectivity())
        drawRegionRecycler()
    }

    private fun drawRegionRecycler() = with(regions_recycler_view) {
        layoutManager = GridLayoutManager(context, 2)
        adapter = MyRegionRecyclerViewAdapter(
            this@RegionFragment,
            viewModel.regionsLiveData
        ) { startCountriesScreen(it) }
    }

    private fun startCountriesScreen(region: Region) = RegionFragmentDirections
        .actionRegionFragmentToCountryFragment(region)
        .let { activity?.findNavController(R.id.nav_host_fragment)?.navigate(it) }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }
}