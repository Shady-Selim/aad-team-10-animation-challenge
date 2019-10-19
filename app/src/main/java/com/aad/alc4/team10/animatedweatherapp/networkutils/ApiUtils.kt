package com.aad.alc4.team10.animatedweatherapp.networkutils

class ApiUtils : RetrofitApi() {

    fun getServiceClass(): RetrofitInterface? {
        return getRetrofit()?.create(RetrofitInterface::class.java)
    }
}