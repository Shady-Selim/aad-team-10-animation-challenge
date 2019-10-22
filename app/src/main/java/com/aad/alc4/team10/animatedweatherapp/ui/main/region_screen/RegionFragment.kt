package com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnCancel
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.aad.alc4.team10.animatedweatherapp.R
import com.aad.alc4.team10.animatedweatherapp.core.showAnimator
import com.aad.alc4.team10.animatedweatherapp.ui.main.dummy.DummyContent.DummyItem
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_region_list.*

class RegionFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[RegionsViewModel::class.java]
    }
    private val errorObjectAnimator by lazy { img_error_region.showAnimator() }

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
        viewModel.loading.observe(this, Observer { onLoading(it) })
        viewModel.getRegions(activity!!.checkConnectivity(), ::onError)
        drawRegionRecycler()
    }

    fun onLoading(loading: Boolean) {
        pb_loading_region.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun onError(error: Boolean?) = if (error == true) {
        //handel error
        showErrorImage()
        showSnackbar()

    } else
        hideErrorImage()

    private fun showErrorImage(): Unit = with(img_error_region) {
        scaleX = 0f
        scaleY = 0f

        animate().apply {
            cancel()
            duration = 500
            scaleX(1f)
            scaleY(1f)
            visibility = View.VISIBLE
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    errorObjectAnimator.start()
                }
            }
            )

        }
    }

    private fun hideErrorImage(): Unit = with(img_error_region) {
        scaleX = 1f
        scaleY = 1f
        animate().apply {
            cancel()
            duration = 500
            scaleX(0f)
            scaleY(0f)
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    errorObjectAnimator.doOnCancel { visibility = View.GONE }
                    errorObjectAnimator.cancel()

                }
            })
        }
    }

    private fun showSnackbar() {
        Snackbar
            .make(regions_recycler_view, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.try_again)) {
                onError(false)
                viewModel.getRegions(activity!!.checkConnectivity(), ::onError)
            }
            .show()
    }


    private fun drawRegionRecycler() = with(regions_recycler_view) {
        layoutManager = GridLayoutManager(context, 1)
        val alphaAdapter = AlphaInAnimationAdapter(
            MyRegionRecyclerViewAdapter(
                this@RegionFragment,
                viewModel.regionsLiveData
            )
        ).apply {
            // Change the durations.
            setDuration(2500)
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