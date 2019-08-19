package com.danshima.savemyq.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Error<out T>(val message: String, val data: T?, val cause: Exception? = null) : Resource<T>()
    data class Loading<out T>(val data: T?) : Resource<T>()
}
