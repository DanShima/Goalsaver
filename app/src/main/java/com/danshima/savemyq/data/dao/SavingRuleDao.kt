package com.danshima.savemyq.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danshima.savemyq.data.SavingRule

@Dao
interface SavingRuleDao {
    @Query("SELECT * from rules_table ORDER BY id")
    fun getAllRules(): List<SavingRule>

    @Insert
    fun insert(goal: SavingRule)

    @Query("DELETE FROM rules_table")
    fun deleteAll()
}