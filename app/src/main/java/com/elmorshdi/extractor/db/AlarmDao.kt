package com.elmorshdi.extractor.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
  interface AlarmDao {

    @Insert
     suspend fun insertAlarm(alarmDisplayModel: AlarmDisplayModel)

    @Query("UPDATE alarm_table SET done=1  WHERE id = :id")
    suspend fun updateDone(id: Int)

    @Query("UPDATE alarm_table SET done=:note  WHERE id = :id")
    suspend fun updateNote(note: String,id: Int)

    @Update
    suspend fun updateAlarm(alarmDisplayModel: AlarmDisplayModel)

    @Query("SELECT * FROM alarm_table  WHERE done = 0 ")
    suspend fun getNextAlarm(): List<AlarmDisplayModel>

    @Query("DELETE FROM alarm_table WHERE id = :id")
     suspend fun deleteAlarm(id: Int)

    @Query("SELECT * FROM alarm_table ")
    suspend fun getAllAlarm(): List<AlarmDisplayModel>


}
