package com.danshima.savemyq.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * expose the service to the rest of the app using a public object [ApiService],
 * and lazily initialize the Retrofit service there.
 */
object ApiService {
    private const val BASE_URL = "http://qapital-ios-testtask.herokuapp.com"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val logging: HttpLoggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging)

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient.build())
    private var retrofit: Retrofit = builder.build()
    val retrofitService: ApiCall by lazy { retrofit.create(ApiCall::class.java) }
}



