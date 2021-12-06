package com.elmorshdi.extractor.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AlarmDisplayModel::class],
    version = 1
)
abstract class Database : RoomDatabase() {
     abstract fun getAlarmDao(): AlarmDao

}