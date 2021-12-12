package com.elmorshdi.extractor.other

import android.Manifest
import android.content.Context
import com.elmorshdi.extractor.db.Date
import com.elmorshdi.extractor.db.Time
import com.elmorshdi.extractor.other.Constants.EMAILREGEX
import com.elmorshdi.extractor.other.Constants.PHONEREGEX
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

object  Utility {

    fun hasPermissions(context: Context) =EasyPermissions.hasPermissions(
        context,
        Manifest.permission.CALL_PHONE
    )
    fun getAllPhones(s: String): ArrayList<String> {
        var all=ArrayList<String>()
        val sp = " "
        val list = s.split(sp)
        for (it in list) {
            val phoneMatcher: Matcher = Pattern.compile(PHONEREGEX).matcher(it)
            var phone = ""
            if (phoneMatcher.find())  {
                phone = phoneMatcher.group(0)!!
                all.add(phone)
            }
        }

        return all
    }
     fun getAllEmail(s: String): ArrayList<String> {
        val all=ArrayList<String>()
        val sp = " "
        val list = s.split(sp)
        for (it in list) {
            val s=it.trim().lowercase()
            val emailMatcher: Matcher = Pattern.compile(EMAILREGEX).matcher(s)
            var email = ""
            if (emailMatcher.find()) {
                email = emailMatcher.group(0)!!
                all.add(email)}
        }
        return all
    }
    fun timeText(time: Time): String{
        val hour=time.hour
        val minute=time.minute
            val h = "%02d".format(if (hour < 12) hour else hour - 12)
            val m = "%02d".format(minute)
            val ampmText=if (hour < 12) "AM" else "PM"
            return "$h:$m $ampmText"
        }
    fun getCalendar(
        time: Time,
        date: Date
    ): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.month)
            set(Calendar.DAY_OF_MONTH, date.day)

        }
        return calendar
    }
    fun getCalendar(
        date: Date
    ): Calendar {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.month)
            set(Calendar.DAY_OF_MONTH, date.day)

        }
        return calendar
    }
    fun getCalendar(
    ): Calendar {
        return Calendar.getInstance()
    }
    fun getID(d : Date, t : Time):Int{

        return  getCalendar(t,d).timeInMillis.toInt()
    }
}