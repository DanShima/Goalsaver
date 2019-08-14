package com.danshima.savemyq.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.data.SavingRule

@Dao
interface SavingRuleDao {
    @Query("SELECT * from rules_table ORDER BY id")
    fun getAllRules(): LiveData<List<SavingRule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rule: SavingRule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRules(rules: List<SavingRule>)

    @Query("DELETE FROM rules_table")
    fun deleteAll()
}