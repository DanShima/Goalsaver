package com.danshima.savemyq.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

//{
//    "goalImageURL": "https://static.qapitalapp.net/assets/ios-staging.api.qapital.com/images/goal/6dc5befb-5389-4d89-ab37-205c83ccf79c.jpg",
//    "userId": 1,
//    "targetAmount": 1500,
//    "currentBalance": 400,
//    "created": [
//    2014,
//    12,
//    5
//    ],
//    "status": "active",
//    "name": "Balloons",
//    "id": 1,
//    "connectedUsers": [2, 3, 4]
//}
@Entity(tableName = "goals_table")
data class SavingGoal(
    @Json(name="goalImageUrl")val url: String,
    val userId: Int,
    val targetAmount: Float? = null,
    val currentBalance: Float,
    @Ignore val created: List<Int>,
    val status: String,
    val name: String,
    @PrimaryKey val id: Int,
    @Ignore val connectedUsers: List<Int>
)

//TODO: need type converter for the Lists