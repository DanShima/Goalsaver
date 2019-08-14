package com.danshima.savemyq.overview

import androidx.lifecycle.*
import com.danshima.savemyq.data.SavingGoal



/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {
    // The internal MutableLiveData String that stores the most recent response
    private val _goal = MutableLiveData<SavingGoal>()
    // The external immutable LiveData
    val goal: LiveData<SavingGoal>
        get() = _goal


    init {
        getGoals()
    }

    private fun getGoals() {
    }
}