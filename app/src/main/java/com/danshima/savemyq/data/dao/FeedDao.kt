package com.danshima.savemyq.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.data.Feed

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: Feed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeeds(feeds: List<Feed>)

    @Query("SELECT * FROM feed_table WHERE userId = :id")
    fun findFeedById(id: Int): LiveData<List<Feed>>

}