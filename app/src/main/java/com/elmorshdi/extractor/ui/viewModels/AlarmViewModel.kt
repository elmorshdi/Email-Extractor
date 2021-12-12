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
class AlarmViewModel
@Inject constructor
    (
    private val mainRepository: MainRepository
) : ViewModel() {

    fun insertAlarm(alarmDisplayModel: AlarmDisplayModel) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.insertRun(alarmDisplayModel)
        Log.d("tag","v:id:${alarmDisplayModel.id}")
    }
    fun updateAlarm(alarmDisplayModel: AlarmDisplayModel) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.updateAlarm(alarmDisplayModel)
        Log.d("tag","v:id:${alarmDisplayModel.id}")
    }
    fun updateNote(note:String,id: Int) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.updateNote(note,id)
    } fun updateDone(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.updateDone(id)
    }
    fun deleteAlarm(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.deleteAlarm(id)
    }
    fun getNextAlarmAsync ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getNextAlarm() }


    fun getAllAlarmAsync ()= CoroutineScope(Dispatchers.IO).async {
        mainRepository.getAllAlarm() }


}