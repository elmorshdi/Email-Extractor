package com.elmorshdi.extractor.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class Time(
    val hour: Int,
    val minute: Int,
         ): Parcelable {


    val timeText: String
        get() {
            val h = "%02d".format(if (hour < 12) hour else hour - 12)
            val m = "%02d".format(minute)

            return "$h:$m"
        }

    val ampmText: String
        get() {
            return if (hour < 12) "AM" else "PM"
        }




}

