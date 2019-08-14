package com.danshima.savemyq.data

import androidx.room.Entity

@Entity(tableName = "rules_table")
data class SavingRule(
    val id: Int,
    val type: String,
    val amount: Float
)