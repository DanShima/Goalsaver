package com.danshima.savemyq.network

import com.danshima.savemyq.data.Feed
import com.danshima.savemyq.data.SavingGoal
import com.danshima.savemyq.data.SavingRule
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCall {
    @GET("/savingsgoals")
    fun getSavingGoals():
            Single<List<SavingGoal>>

    @GET("/savingsrules")
    fun getSavingsRules():
            Single<List<SavingRule>>

    @GET("/savingsgoals/{id}/feed")
    fun getFeeds(
        @Path("id") id: String
    ): Single<List<Feed>>


}