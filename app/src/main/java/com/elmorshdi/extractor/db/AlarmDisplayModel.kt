package com.elmorshdi.extractor.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "alarm_table")
data class AlarmDisplayModel(
    @SerializedName("date")
    var date: Date,
    @SerializedName("time")
    var time: Time,
    @SerializedName("title")
    var title:String,
    @SerializedName("summary")
    var summary:String,
    @SerializedName("note")
    var note:String,
    @SerializedName("done")
    var done:Boolean,
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
):  Parcelable {



}

