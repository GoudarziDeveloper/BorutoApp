package com.tinydeveloper.borutoapp.data.local

import androidx.room.TypeConverter

class DatabaseConverter {
    private val separator = ","
    @TypeConverter
    fun convertListToString(list: List<String>): String{
        val stringBuilder = StringBuilder()
        list.forEach {
            stringBuilder.append(it).append(separator)
        }
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}