package com.danshima.savemyq.db

import androidx.room.TypeConverter


object TypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map { s ->
                try {
                    s.toInt()
                } catch (ex: NumberFormatException) {

                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}
