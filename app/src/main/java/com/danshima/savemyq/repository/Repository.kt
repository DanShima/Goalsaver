package com.danshima.savemyq.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danshima.savemyq.common.AppExecutors
import com.danshima.savemyq.api.*
import com.danshima.savemyq.common.NetworkBoundResource
import com.danshima.savemyq.common.toNetworkSingle
import com.danshima.savemyq.db.FeedDao
import com.danshima.savemyq.db.SavingsGoalDao
import com.danshima.savemyq.db.SavingsRuleDao
import com.danshima.savemyq.model.Feed
import com.danshima.savemyq.model.Resource
import com.danshima.savemyq.model.SavingsGoal
import com.danshima.savemyq.model.SavingsRule

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: Api,
    private val appExecutors: AppExecutors,
    private val savingsRuleDao: SavingsRuleDao,
    private val savingsGoalDao: SavingsGoalDao,
    private val feedDao: FeedDao
) {

    val compositeDisposable = CompositeDisposable()

    fun getRules(forceReload: Boolean = false): LiveData<Resource<List<SavingsRule>>> {
        return object : NetworkBoundResource<List<SavingsRule>, SavingsRulesEntity>(appExecutors) {
            override fun saveCallResult(item: SavingsRulesEntity) {
                savingsRuleDao.insertRules(item.savingsRules)
            }

            override fun shouldFetch(data: List<SavingsRule>?): Boolean = data.isNullOrEmpty() || forceReload

            override fun loadFromDb(): LiveData<List<SavingsRule>> = savingsRuleDao.fetchAllRules()

            override fun createCall(): LiveData<ApiResponse<SavingsRulesEntity>> {
                val entity: MutableLiveData<ApiResponse<SavingsRulesEntity>> = MutableLiveData()
                val disposable = api.getSavingRules()
                    .toNetworkSingle()
                    .subscribe({ success ->
                        entity.postValue(ApiSuccessResponse(success))

                    }) { throwable ->
                        entity.postValue(ApiResponse.create(throwable))
                    }
                compositeDisposable.add(disposable)
                return entity
            }

        }.asLiveData()


    }

    fun getGoals(forceReload: Boolean = false): LiveData<Resource<List<SavingsGoal>>> {
        return object : NetworkBoundResource<List<SavingsGoal>, SavingsGoalEntity>(appExecutors) {
            override fun saveCallResult(item: SavingsGoalEntity) {
                savingsGoalDao.insertGoals(item.savingsGoals)
            }
            override fun shouldFetch(data: List<SavingsGoal>?): Boolean = data.isNullOrEmpty() || forceReload

            override fun loadFromDb(): LiveData<List<SavingsGoal>> = savingsGoalDao.fetchAllGoals()

            override fun createCall(): LiveData<ApiResponse<SavingsGoalEntity>> {
                val entity: MutableLiveData<ApiResponse<SavingsGoalEntity>> = MutableLiveData()
                val disposable = api.getSavingsGoal()
                    .toNetworkSingle()
                    .subscribe({ success ->
                        entity.postValue(ApiSuccessResponse(success))

                    }) { throwable ->
                        entity.postValue(ApiResponse.create(throwable))
                    }
                compositeDisposable.add(disposable)
                return entity
            }
        }.asLiveData()
    }


    fun getFeeds(id: Int, forceReload: Boolean = false): LiveData<Resource<List<Feed>>> {
        return object : NetworkBoundResource<List<Feed>, FeedEntity>(appExecutors) {
            override fun saveCallResult(item: FeedEntity) {
                item.feed.forEach {
                    it.userId = id
                    feedDao.insert(it)
                }
            }

            override fun shouldFetch(data: List<Feed>?): Boolean = data.isNullOrEmpty() || forceReload

            override fun loadFromDb(): LiveData<List<Feed>> = feedDao.findFeedById(id)

            override fun createCall(): LiveData<ApiResponse<FeedEntity>> {
                val entity: MutableLiveData<ApiResponse<FeedEntity>> = MutableLiveData()
                val disposable = api.getSavingsGoalFeed(id)
                    .toNetworkSingle()
                    .subscribe({ success ->
                        entity.postValue(ApiSuccessResponse(success))

                    }) { throwable ->
                        entity.postValue(ApiResponse.create(throwable))
                    }
                compositeDisposable.add(disposable)
                return entity
            }
        }.asLiveData()
    }

    fun clear() {
        // Using clear will clear all, but can accept new disposable
        // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
        compositeDisposable.clear()
    }
}