package com.danshima.savemyq.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danshima.savemyq.data.SavingGoal

@Dao
interface SavingGoalDao {
    @Query("SELECT * from goals_table ORDER BY name ASC")
    fun getAllGoals(): List<SavingGoal>

    @Insert
    fun insert(goal: SavingGoal)

    @Query("DELETE FROM goals_table")
    fun deleteAll()
}