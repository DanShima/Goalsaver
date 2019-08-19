package com.danshima.savemyq.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import com.danshima.savemyq.db.TypeConverter

import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["id"])
@TypeConverters(TypeConverter::class)
@Parcelize
data class SavingsGoal(
    val goalImageURL: String,
    val userId: Int,
    val targetAmount: Float?,
    val currentBalance: Float,
    val status: String,
    val name: String,
    val id: Int,
    val connectedUsers: List<Int>?,
    val created: List<Int>
) : Parcelable
