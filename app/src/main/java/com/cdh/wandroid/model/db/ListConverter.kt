package com.cdh.wandroid.model.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by chidehang on 2020-01-08
 */
class ListConverter {

    @TypeConverter
    fun stringToList(value: String): List<Any> {
        var type = object : TypeToken<List<Any>>(){}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun listToString(value: List<Any>): String {
        return Gson().toJson(value)
    }
}