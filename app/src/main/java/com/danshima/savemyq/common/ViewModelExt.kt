package com.danshima.savemyq.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.provideViewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory).get(T::class.java)