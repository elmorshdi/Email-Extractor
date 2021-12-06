package com.elmorshdi.extractor.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.elmorshdi.extractor.ManageAlarms
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.other.Constants.ALARM_ACTION
import com.elmorshdi.extractor.other.Constants.BOOT_COMPLETED_ACTION
import com.elmorshdi.extractor.ui.viewModels.AddAlarmViewModel
import com.elmorshdi.extractor.ui.viewModels.CalendarViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
 class AlarmReceiver : BroadcastReceiver()  {
        @Inject
    lateinit var viewModel: CalendarViewModel
    lateinit var viewModel2: AddAlarmViewModel

    lateinit var model:AlarmDisplayModel
    companion object {
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("tag", " service opened")

        createNotificationChannel(context)
        if (intent.action.equals(ALARM_ACTION)) {
            val title = intent.getStringExtra("TITLE")
            val summary = intent.getStringExtra("SUMMARY")
            Log.d("tag", "id:ALARM_ACTION")
            model = Gson().fromJson(intent.extras?.getString("MODEL"), AlarmDisplayModel::class.java)

            notifyNotification(context, summary = model.summary, title = model.title)
            model.done=true
            viewModel2.updateAlarm(model)



        } else if (intent.action.equals(BOOT_COMPLETED_ACTION)) {
            val send=ManageAlarms()
            Log.d("tag", "id:boot done")

            val list= runBlocking (Dispatchers.IO){  viewModel.getNextAlarmAsync().await() }

            for (a in list){
                send.sendToSystem(a,context)
                Log.d("tag", "id:${a.date}added again")

            }

        }
    }

    private fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                " Alarm",
                NotificationManager.IMPORTANCE_HIGH
            )

            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context,summary: String,title:String) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(summary)
                .setSound( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_extract)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())

        }

    }

}
