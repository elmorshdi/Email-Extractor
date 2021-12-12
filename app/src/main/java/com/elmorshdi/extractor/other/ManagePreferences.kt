package com.elmorshdi.extractor.other

import android.content.SharedPreferences
import android.util.Log
import androidx.preference.ListPreference
import javax.inject.Inject


class ManagePreferences
@Inject constructor(private val pref: SharedPreferences){


      fun fillListPreference(listPreference: ListPreference, array: MutableSet<String>) {
        val entries: Array<CharSequence?> = arrayOfNulls(array.size)
          for ((i, category) in array.withIndex()) {
            entries[i] = category
          }
        listPreference.entries = entries
        listPreference.entryValues = entries
    }

      fun getArray(key: String): MutableSet<String> {
        var set: MutableSet<String> = HashSet()
        set = pref.getStringSet(key, set) as MutableSet<String>
        val array: Array<String> = set.toTypedArray()
        return set
    }

      fun addToPref(array: MutableSet<String>, key: String) {

        val editor: SharedPreferences.Editor = pref.edit()
        editor.putStringSet(key, array)
        editor.apply()
        Log.d("msg", getArray(key).size.toString())

    }
}