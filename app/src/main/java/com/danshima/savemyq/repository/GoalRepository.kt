package com.danshima.savemyq.repository

import androidx.lifecycle.LiveData
import com.danshima.savemyq.data.SavingGoal
import com.danshima.savemyq.data.dao.SavingGoalDao


class GoalRepository(private val goalDao: SavingGoalDao) {
    val allGoals: LiveData<List<SavingGoal>> = goalDao.getAllGoals()


}