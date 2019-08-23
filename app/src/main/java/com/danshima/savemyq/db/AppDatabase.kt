package com.danshima.savemyq.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danshima.savemyq.model.Feed
import com.danshima.savemyq.model.SavingsGoal
import com.danshima.savemyq.model.SavingsRule


@Database(
    entities = [SavingsRule::class, Feed::class, SavingsGoal::class], version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun savingsGoalDao(): SavingsGoalDao
    abstract fun savingsRuleDao(): SavingsRuleDao
}
