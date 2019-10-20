package com.aad.alc4.team10.animatedweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aad.alc4.team10.animatedweatherapp.ui.main.region_screen.RegionFragment
import com.aad.alc4.team10.animatedweatherapp.ui.main.dummy.DummyContent

class MainActivity : AppCompatActivity(), RegionFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

}
