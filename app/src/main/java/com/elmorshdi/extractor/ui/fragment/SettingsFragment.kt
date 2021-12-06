package com.elmorshdi.extractor.ui.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.elmorshdi.extractor.R


class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var subjectList: ListPreference
    private lateinit var msgList: ListPreference
    private lateinit var pref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        fillListPreference(subjectList, getArray(R.string.key_subject_array.toString()))

        fillListPreference(msgList, getArray(R.string.key_msg_array.toString()))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        subjectList = findPreference(getString(R.string.key_select_subject))!!
        msgList = findPreference(getString(R.string.key_select_msg))!!

        pref = requireActivity().getSharedPreferences("USER", MODE_PRIVATE)

        val addSubject = findPreference<EditTextPreference>(getString(R.string.key_add_subject))
        addSubject?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            var subjectArray: MutableSet<String> = getArray(R.string.key_subject_array.toString())
            if (subjectArray.contains(newSub)) {
                Toast.makeText(
                    requireContext(), "This Subject already exists, please select from below",
                    Toast.LENGTH_SHORT
                ).show()

                false   // false: reject the new value.
            } else {
                subjectArray.add(newSub)
                addToPref(subjectArray, R.string.key_subject_array.toString())
                fillListPreference(subjectList, subjectArray)

                true// true: accept the new value.
            }

        }
        val addMsg = findPreference<EditTextPreference>(getString(R.string.key_add_msg))
        addMsg?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            var msgArray: MutableSet<String> = getArray(R.string.key_msg_array.toString())
            if (msgArray.contains(newSub)) {
                Toast.makeText(
                    requireContext(), "This Message already exists, please select from below",
                    Toast.LENGTH_SHORT
                ).show()

                false   // false: reject the new value.
            } else {
                msgArray.add(newSub)
                addToPref(msgArray, R.string.key_msg_array.toString())
                fillListPreference(msgList, msgArray)
                true// true: accept the new value.
            }

        }

    }


    private fun fillListPreference(listPreference: ListPreference, array: MutableSet<String>) {
        val entries: Array<CharSequence?> = arrayOfNulls(array.size)
        var i = 0
        for (category in array) {
            entries[i] = category
            i++
        }
        listPreference.entries = entries
        listPreference.entryValues = entries
    }

    private fun getArray(key: String): MutableSet<String> {
        var set: MutableSet<String> = HashSet()
        set = pref.getStringSet(key, set) as MutableSet<String>
        val array: Array<String> = set.toTypedArray()
return set
    }

    private fun addToPref(array: MutableSet<String>, key: String) {

        val editor: SharedPreferences.Editor = pref.edit()
        editor.putStringSet(key, array)
        editor.apply()
        Log.d("msg", getArray(key).size.toString())

    }

}

