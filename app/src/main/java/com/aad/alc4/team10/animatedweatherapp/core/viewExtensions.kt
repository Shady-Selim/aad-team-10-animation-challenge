package com.aad.alc4.team10.animatedweatherapp.core

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View


fun View.showAnimator(): ObjectAnimator {
    val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.2f)
    val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.2f)
    val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)

    return objectAnimator.apply {
        duration = 2000
        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.REVERSE


    }

}
