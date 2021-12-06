package com.elmorshdi.extractor.repository

import android.util.Log
import com.elmorshdi.extractor.db.AlarmDao
import com.elmorshdi.extractor.db.AlarmDisplayModel
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {
      suspend fun insertRun(alarmDisplayModel: AlarmDisplayModel) {
          alarmDao.insertAlarm(alarmDisplayModel)
          Log.d("tag"," m:id:${alarmDisplayModel.id}")

      }
    suspend fun updateAlarm(a: AlarmDisplayModel) {
        alarmDao.updateAlarm(a)

    }

      suspend fun deleteAlarm(alarmDisplayModel: AlarmDisplayModel) {

          alarmDao.deleteAlarm(alarmDisplayModel.id)
          Log.d("tag", " delete:id:${alarmDisplayModel.id}")
      }
    suspend fun searchByDate(s: String):List<AlarmDisplayModel> =
        alarmDao.searchByDate("%+$s+%")

    suspend  fun getDoneAlarm ()= alarmDao.getDoneAlarm()
    suspend  fun getAllAlarm ()= alarmDao.getAllAlarm()
    suspend  fun getAllDate() = alarmDao.getAllDate()
    suspend  fun getNextAlarm() = alarmDao.getNextAlarm()

}


