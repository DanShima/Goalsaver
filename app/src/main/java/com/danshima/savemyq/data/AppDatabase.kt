package com.danshima.savemyq.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.danshima.savemyq.data.dao.FeedDao
import com.danshima.savemyq.data.dao.SavingGoalDao
import com.danshima.savemyq.data.dao.SavingRuleDao


/**
 * The Room database for this app
 */
@Database(entities = [SavingGoal::class, SavingRule::class, Feed::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savingRuleDao(): SavingRuleDao
    abstract fun savingGoalDao(): SavingGoalDao
    abstract fun feedDao(): FeedDao

    companion object {
        // For Singleton instantiation
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "goal_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}