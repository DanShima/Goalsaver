package com.danshima.savemyq.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.model.SavingsRule


@Dao
interface SavingsRuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rule: SavingsRule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRules(rules: List<SavingsRule>)

    @Query("SELECT * FROM savingsrule")
    fun fetchAllRules(): LiveData<List<SavingsRule>>
}