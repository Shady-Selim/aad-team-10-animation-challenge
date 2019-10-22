package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.ui.main.dummy.DummyContent.DummyItem
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_region_list.*

class RegionFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[RegionsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply { duration = 700 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_region_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        activity?.title = "Regions"

        viewModel.getRegions(activity!!.checkConnectivity())
        drawRegionRecycler()
    }

    private fun drawRegionRecycler() = with(regions_recycler_view) {
        layoutManager = GridLayoutManager(context, 1)
        val alphaAdapter = AlphaInAnimationAdapter(MyRegionRecyclerViewAdapter(
            this@RegionFragment,
            viewModel.regionsLiveData
        )
        ).apply {
            // Change the durations.
            setDuration(3000)
            // Disable the first scroll mode.
            setFirstOnly(false)
        }
        adapter = ScaleInAnimationAdapter(alphaAdapter)

    }


    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }
}