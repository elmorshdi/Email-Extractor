package com.elmorshdi.extractor.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CalendarViewModel
@Inject constructor
    (
    private val mainRepository: MainRepository
    ) : ViewModel() {
      fun getNextAlarmAsync ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getNextAlarm() }

    fun getAllDateAsync ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getAllDate() }
    fun getAllAlarmAsync ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getAllAlarm() }
    fun getDoneAlarm ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getDoneAlarm() }
    suspend fun searchByDate (s: String)=CoroutineScope(Dispatchers.IO).async {
        Log.d("tag","v:id:${ mainRepository.searchByDate(s)}")

            mainRepository.searchByDate(s)
        }

//    fun searchByDate (s: String)= CoroutineScope(Dispatchers.IO).async {
//        Log.d("tag", "list:vm:${mainRepository.searchByDate(s).toString()}")
//
//        mainRepository.searchByDate(s)
//    }


    fun deleteAlarm(alarmDisplayModel: AlarmDisplayModel) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.deleteAlarm(alarmDisplayModel)
        Log.d("tag","v:id:${alarmDisplayModel.id}")
    }
    fun insertAlarm(alarmDisplayModel: AlarmDisplayModel) = viewModelScope.launch(Dispatchers.IO) {
              mainRepository.insertRun(alarmDisplayModel)
              Log.d("tag","v:id:${alarmDisplayModel.id}")
    }
}
