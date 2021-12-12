package com.elmorshdi.extractor.other

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.other.Constants.ALARM_ACTION
import com.elmorshdi.extractor.other.Utility.getCalendar
import com.elmorshdi.extractor.service.AlarmReceiver
import com.google.gson.Gson

class ManageAlarms (){
     @SuppressLint("UnspecifiedImmutableFlag")
     fun sendToSystem(
        newModel: AlarmDisplayModel,
        context: Context
    ) {
         val date=newModel.date
         val time=newModel.time

         val calendar =getCalendar(time, date)
         val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
         intent.putExtra("MODEL", Gson().toJson(newModel))

        intent.action=ALARM_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            context, newModel.id,
            intent, PendingIntent.FLAG_ONE_SHOT
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


    fun cancelAlarm (
        newModel: AlarmDisplayModel,
        context: Context
    ){

            val pendingIntent = PendingIntent.getBroadcast(context, newModel.id,
                Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
            pendingIntent?.cancel()

    }

}
