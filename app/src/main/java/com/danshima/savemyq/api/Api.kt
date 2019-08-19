package com.danshima.savemyq.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/savingsgoals")
    fun getSavingsGoal(): Single<SavingsGoalEntity>

    @GET("/savingsrules")
    fun getSavingRules(): Single<SavingsRulesEntity>

    @GET("/savingsgoals/{id}/feed")
    fun getSavingsGoalFeed(@Path("id") id: Int): Single<FeedEntity>
}