package com.danshima.savemyq.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This resource will return an object with one key, feed.
 * This key maps to a list of feed items for goal details screen
 */
@Entity(tableName = "feed_table")
data class Feed(
    @PrimaryKey val id: String,
    val type: String,
    val timestamp: String,
    val message: String,
    val amount: Float,
    val userId: Int,
    val savingsRuleId: Int)