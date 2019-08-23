package com.danshima.savemyq.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.danshima.savemyq.repository.Repository
import com.danshima.savemyq.model.Resource
import com.danshima.savemyq.model.SavingsGoal

import javax.inject.Inject

class OverviewViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _shouldFetchGoals = MutableLiveData<Any>()

    fun fetchData() {
        _shouldFetchGoals.postValue(Any())
    }

    fun fetchGoals(forceReload: Boolean = false) {
        _shouldFetchGoals.postValue(forceReload)
    }

    val savingGoals: LiveData<Resource<List<SavingsGoal>>> = Transformations.switchMap(_shouldFetchGoals) {
        if (it is Boolean && it) {
            repository.getGoals(true)
        } else {
            repository.getGoals()
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }
}