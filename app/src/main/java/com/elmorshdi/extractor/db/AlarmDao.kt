package com.elmorshdi.extractor.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
  interface AlarmDao {

    @Insert
     suspend fun insertAlarm(alarmDisplayModel: AlarmDisplayModel)
//    @Query("UPDATE alarm_table SET time=:time & note=:note & title=:title & summary =:summary WHERE id = :id")
     @Update
    suspend fun updateAlarm(alarmDisplayModel: AlarmDisplayModel)
    @Query("DELETE FROM alarm_table WHERE id = :id")
     suspend fun deleteAlarm(id: Int)

    @Query("SELECT * FROM alarm_table ")
   suspend fun getAllAlarm(): List<AlarmDisplayModel>
    @Query("SELECT date FROM alarm_table")
    suspend fun getAllDate(): List<String>
    @Query("SELECT * FROM alarm_table  WHERE done = 0 ")
    suspend fun getNextAlarm(): List<AlarmDisplayModel>
    @Query("SELECT * FROM alarm_table  WHERE done = 1 ")
    suspend fun getDoneAlarm(): List<AlarmDisplayModel>
    @Query("SELECT * FROM alarm_table  WHERE date LIKE :search ")
    suspend fun searchByDate(search:String?): List<AlarmDisplayModel>

}
