package com.danshima.savemyq.model

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class SavingsRule(val id: Int, val type: String, val amount: Float)