package com.elmorshdi.extractor.repository

import android.util.Log
import com.elmorshdi.extractor.db.AlarmDao
import com.elmorshdi.extractor.db.AlarmDisplayModel
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val alarmDao: AlarmDao) {

    suspend fun insertRun(alarmDisplayModel: AlarmDisplayModel) {
          alarmDao.insertAlarm(alarmDisplayModel)
    }

    suspend fun updateAlarm(a: AlarmDisplayModel) {
        alarmDao.updateAlarm(a)
    }

    suspend fun updateNote(note: String,id: Int) {
        alarmDao.updateNote(note, id)
    }

    suspend fun updateDone(id: Int) {
        alarmDao.updateDone(id)
        Log.d("tag","mr:id:${id}")

    }
    suspend  fun getNextAlarm() = alarmDao.getNextAlarm()

    suspend fun deleteAlarm(id: Int) {
          alarmDao.deleteAlarm(id)
    }

     suspend  fun getAllAlarm ()= alarmDao.getAllAlarm()

}


