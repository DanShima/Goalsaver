package com.danshima.savemyq.common

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


fun <T> Single<T>.toNetworkSingle(): Single<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

