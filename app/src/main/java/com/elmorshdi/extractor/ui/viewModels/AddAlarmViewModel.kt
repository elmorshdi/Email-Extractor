package com.elmorshdi.extractor.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddAlarmViewModel
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
    fun deleteAlarm(alarmDisplayModel: AlarmDisplayModel) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.deleteAlarm(alarmDisplayModel)
        Log.d("tag","v:id:${alarmDisplayModel.id}")
    }
}