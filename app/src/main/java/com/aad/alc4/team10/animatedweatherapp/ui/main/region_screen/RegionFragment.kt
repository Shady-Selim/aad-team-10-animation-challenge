package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import Region
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aad.alc4.team10.animatedweatherapp.R

import com.aad.alc4.team10.animatedweatherapp.ui.main.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_region_list.*

public val EXPORTED_REGION =
    "com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.exported.region"

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
        val bundle = Bundle()
        if (savedInstanceState == null) drawRegionRecycler(bundle)
        else drawRegionRecycler(savedInstanceState)

    }

    private fun drawRegionRecycler(bundle: Bundle?) = with(regions_recycler_view) {
        layoutManager = GridLayoutManager(context, 2)
        adapter = MyRegionRecyclerViewAdapter(
            this@RegionFragment,
            viewModel.regionsLiveData
        ) { onRegionClick(bundle, it) }
    }

    private fun onRegionClick(bundle: Bundle?, region: Region) = bundle
        .apply { bundle?.putParcelable(EXPORTED_REGION, region) }
        .let { findNavController().navigate(R.id.action_regionFragment_to_countryFragment, it) }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }
}