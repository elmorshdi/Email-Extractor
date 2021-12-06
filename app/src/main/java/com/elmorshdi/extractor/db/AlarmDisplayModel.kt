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
    var date: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("title")
    var title:String,
    @SerializedName("summary")
    var summary:String,
    @SerializedName("note")
    var note:String,
    @SerializedName("done")
    var done:Boolean,
    @SerializedName("id")
    var id: Int,
    @SerializedName("roomId")
    @PrimaryKey(autoGenerate = true)
    var roomId:Int = 0
):  Parcelable {



}

