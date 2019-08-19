package com.danshima.savemyq.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danshima.savemyq.model.Feed


@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: Feed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeeds(feeds: List<Feed>)

    @Query("SELECT * FROM feed WHERE UserId = :id")
    fun findFeedById(id: Int): LiveData<List<Feed>>
}