package com.danshima.savemyq.network

import android.content.Context
import android.net.ConnectivityManager

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()
}
