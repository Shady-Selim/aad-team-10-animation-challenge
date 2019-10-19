package com.aad.alc4.team10.animatedweatherapp.networkutils

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


open class RetrofitApi {

    val baseUrl = "" //TODO : please add the baseUrl here

    private val moshi: Moshi = Moshi.Builder().build()

    fun getRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


}