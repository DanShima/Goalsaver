package com.danshima.savemyq.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.model.SavingsGoal


@Dao
interface SavingsGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goal: SavingsGoal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(rules: List<SavingsGoal>)

    @Query("SELECT * FROM savingsgoal")
    fun fetchAllGoals(): LiveData<List<SavingsGoal>>

    @Query("SELECT * FROM savingsgoal")
    fun fetchAllGoalsTest(): List<SavingsGoal>

}