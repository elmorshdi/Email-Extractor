package com.elmorshdi.extractor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [AlarmDisplayModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
     abstract fun getAlarmDao(): AlarmDao

}