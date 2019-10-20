package com.weather.useecasses.engine

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> T.toMutableLiveData(): MutableLiveData<T> = MutableLiveData<T>().also { it.postValue(this) }


fun <T> Call<T>.getData(
    success: (T?) -> Unit = {},
    error: (Throwable) -> Unit = {}
) = enqueue(
    object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) = error(t)
        override fun onResponse(call: Call<T>, response: Response<T>) = success(response.body())
    }
)
