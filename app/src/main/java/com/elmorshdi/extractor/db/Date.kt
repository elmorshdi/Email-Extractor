package com.elmorshdi.extractor.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(
     val year: Int,
    val month: Int,
    val day: Int,
    ): Parcelable {


    val dateText: String get() { return "$day/$month/$year" }





}

