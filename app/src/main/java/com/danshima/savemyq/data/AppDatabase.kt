package com.danshima.savemyq.data

import android.content.Context
import androidx.databinding.adapters.Converters
import com.danshima.savemyq.data.dao.FeedDao
import com.danshima.savemyq.data.dao.SavingGoalDao
import com.danshima.savemyq.data.dao.SavingRuleDao


/**
 * The Room database for this app
 */
@Database(entities = [SavingGoal::class, SavingRule::class, Feed::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savingRuleDao(): SavingRuleDao
    abstract fun savingGoalDao(): SavingGoalDao
    abstract fun feedDao(): FeedDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "saving_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}