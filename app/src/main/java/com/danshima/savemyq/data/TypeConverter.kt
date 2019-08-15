package com.danshima.savemyq.data

import android.util.Log
import androidx.room.TypeConverter

object TypeConverter {

    @TypeConverter @JvmStatic
    fun stringToIntList(data: String): List<Int> {
        return data.split(",").mapNotNull { s ->
            try {
                s.toInt()
            } catch (ex: NumberFormatException) {
                Log.e("TypeConverter", "$ex: Fail to convert $s to number")
                null
            }
        }

    }

    @TypeConverter @JvmStatic
    fun intListToString(list: List<Int>): String {
        return list.joinToString(",")
    }
}
