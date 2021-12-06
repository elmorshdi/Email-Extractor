package com.elmorshdi.extractor.other

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.other.Constants.ALARM_ACTION
import com.elmorshdi.extractor.service.AlarmReceiver
import com.google.gson.Gson
import java.util.*

class ManageAlarms (){
     @SuppressLint("UnspecifiedImmutableFlag")
     fun sendToSystem(
        newModel: AlarmDisplayModel,
        context: Context
    ) {
         val date=newModel.date.split("/")
         val time=newModel.time.split(":")

         val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time[0].toInt())
            set(Calendar.MINUTE, time[1].toInt())
            set(Calendar.YEAR, date[2].toInt())
            set(Calendar.MONTH, date[1].toInt())
            set(Calendar.DAY_OF_MONTH, date[0].toInt())


             if (before(Calendar.getInstance())) {
                Log.d("tag","alarm is done ")
                add(Calendar.DATE, 1)
            }
        }
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
         intent.putExtra("MODEL", Gson().toJson(newModel))

        intent.action=ALARM_ACTION
        val pendingIntent = PendingIntent.getBroadcast(
            context, newModel.id,
            intent, PendingIntent.FLAG_ONE_SHOT
        )
//         intent.putExtra(
//            "TITLE",newModel.title
//        )
//         intent.putExtra(
//             "SUMMARY",newModel.summary
//         )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
    fun cancelAlarm (
        newModel: AlarmDisplayModel,
        context: Context
    ){

            val pendingIntent = PendingIntent.getBroadcast(context, newModel.id.toInt(),
                Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
            pendingIntent?.cancel()

    }
    fun getID(date:String, time:String):Int{
        val d=date.split("/")
        val t=time.split(":")
//
//        val calendar = Calendar.getInstance().apply {
//            set(Calendar.HOUR_OF_DAY, t[0].toInt())
//            set(Calendar.MINUTE, t[1].toInt())
//            set(Calendar.YEAR, d[2].toInt())
//            set(Calendar.MONTH, d[1].toInt())
//            set(Calendar.DAY_OF_MONTH, d[0].toInt())
//        }

        return (d[1]+d[0]+t[0]+t[1]).toInt()
    }
}
