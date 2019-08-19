package com.danshima.savemyq.model

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class Feed(
    val id: String,
    val type: String,
    val timestamp: String,
    val message: String,
    val amount: Float,
    var userId: Int,
    val savingsRuleId: Int?
)