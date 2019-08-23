package com.danshima.savemyq.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.danshima.savemyq.model.Feed
import com.danshima.savemyq.model.Resource
import com.danshima.savemyq.model.SavingsGoal
import com.danshima.savemyq.model.SavingsRule
import com.danshima.savemyq.repository.Repository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _fetchFeed = MutableLiveData<Pair<Int, Boolean>>()
    private val _shouldFetchRules = MutableLiveData<Boolean>()

    fun fetchData() {
        _shouldFetchRules.postValue(false)
    }

    fun fetchFeed(id: Int, forceReload: Boolean = false) {
        _fetchFeed.postValue(Pair(id, forceReload))
    }

    fun fetchRules(forceReload: Boolean = false) {
        _shouldFetchRules.postValue(forceReload)
    }

    val savingRules: LiveData<Resource<List<SavingsRule>>> = Transformations.switchMap(_shouldFetchRules) {
        repository.getRules(it)
    }

    val feed: LiveData<Resource<List<Feed>>> = Transformations.switchMap(_fetchFeed) {
        repository.getFeeds(it.first, it.second)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }

}