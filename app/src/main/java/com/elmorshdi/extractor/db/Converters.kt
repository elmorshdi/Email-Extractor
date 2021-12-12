package com.elmorshdi.extractor.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun dateToGson(date: Date): String {
        return Gson().toJson(date)
    }
    @TypeConverter
    fun timeToGson(time: Time): String {
        return Gson().toJson(time)
    }
    @TypeConverter
    fun gsonToDate(string: String): Date {
        return Gson().fromJson(string,Date::class.java)

    }
    @TypeConverter
    fun gsonToTime(string: String): Time {
        return Gson().fromJson(string,Time::class.java)

    }
}