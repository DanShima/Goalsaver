package com.danshima.savemyq.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.data.SavingGoal

@Dao
interface SavingGoalDao {
    @Query("SELECT * from goals_table")
    fun getAllGoals(): LiveData<List<SavingGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goal: SavingGoal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(goals: List<SavingGoal>)

    @Query("DELETE FROM goals_table")
    fun deleteAll()
}