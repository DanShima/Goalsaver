package com.danshima.savemyq.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.danshima.savemyq.repository.Repository
import com.danshima.savemyq.model.Feed
import com.danshima.savemyq.model.Resource
import com.danshima.savemyq.model.SavingsGoal
import com.danshima.savemyq.model.SavingsRule

import javax.inject.Inject

class OverviewViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _triggerFetchRules = MutableLiveData<Boolean>()
    private val _triggerFetchGoals = MutableLiveData<Any>()
    private val _fetchFeed = MutableLiveData<Pair<Int, Boolean>>()

    fun fetchData() {
        _triggerFetchRules.postValue(false)
        _triggerFetchGoals.postValue(Any())
    }

    fun fetchGoals(forceReload: Boolean = false) {
        _triggerFetchGoals.postValue(forceReload)
    }

    fun fetchFeed(id: Int, forceReload: Boolean = false) {
        _fetchFeed.postValue(Pair(id, forceReload))
    }

    fun fetchRules(forceReload: Boolean = false) {
        _triggerFetchRules.postValue(forceReload)
    }

    val savingRules: LiveData<Resource<List<SavingsRule>>> = Transformations.switchMap(_triggerFetchRules) {
        repository.fetchSavingRules(it)
    }

    val savingGoals: LiveData<Resource<List<SavingsGoal>>> = Transformations.switchMap(_triggerFetchGoals) {
        if (it is Boolean && it) {
            repository.fetchSavingsGoal(true)
        } else {
            repository.fetchSavingsGoal()
        }
    }

    val feed: LiveData<Resource<List<Feed>>> = Transformations.switchMap(_fetchFeed) {
        repository.fetchSavingsGoalsFeed(it.first, it.second)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }
}