package com.danshima.savemyq.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rules_table")
data class SavingRule(
    @PrimaryKey val id: Int,
    val type: String,
    val amount: Float
)