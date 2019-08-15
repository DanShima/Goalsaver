package com.danshima.savemyq.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.danshima.savemyq.data.AppDatabase
import com.danshima.savemyq.data.SavingGoal
import com.danshima.savemyq.data.dao.SavingGoalDao
import com.danshima.savemyq.network.ApiService
import com.danshima.savemyq.repository.GoalRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    // The internal MutableLiveData String that stores the most recent response
    private val _goals = MutableLiveData<List<SavingGoal>>()
    private lateinit var repository: GoalRepository
    private lateinit var goalDao: SavingGoalDao
    // The external immutable LiveData
    val goals: LiveData<List<SavingGoal>>
        get() = _goals


    init {
        val goalDao = AppDatabase.getDatabase(application).savingGoalDao()
        repository = GoalRepository(goalDao)
        //goals = repository.allGoals
        getGoals()
    }

    private fun getGoals() {
       val goalsDisposable = ApiService.retrofitService.getSavingGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    list ->
                    _goals.value = list
                }, {
                    Log.e("oh no", "${it.message}")
                }
            )
        compositeDisposable.add(goalsDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}