package com.danshima.savemyq.data

/**
 * This resource will return an object with one key, feed.
 * This key maps to a list of feed items for goal details screen
 */
data class Feed(
    val id: String,
    val type: String,
    val timestamp: String,
    val message: String,
    val amount: Float,
    val userId: Int,
    val savingsRuleId: Int)