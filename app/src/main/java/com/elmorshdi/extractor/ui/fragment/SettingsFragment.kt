package com.elmorshdi.extractor.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.other.MangePref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var subjectList: ListPreference
    private lateinit var msgList: ListPreference
    private lateinit var waList: ListPreference

    @Inject
    lateinit var mangePref: MangePref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        mangePref.fillListPreference(subjectList, mangePref.getArray(R.string.key_subject_array.toString()))
        mangePref.fillListPreference(msgList, mangePref.getArray(R.string.key_msg_array.toString()))
        mangePref.fillListPreference(waList, mangePref.getArray(R.string.key_msg_array_wa.toString()))

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        subjectList = findPreference(getString(R.string.key_select_subject))!!
        msgList = findPreference(getString(R.string.key_select_msg))!!
        waList = findPreference(getString(R.string.key_select_msg_wa))!!


        val addSubject = findPreference<EditTextPreference>(getString(R.string.key_add_subject))
        addSubject?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            val subjectArray: MutableSet<String> = mangePref.getArray(R.string.key_subject_array.toString())
            add(subjectArray, newSub,subjectList,R.string.key_subject_array.toString())

        }
        val addMsg = findPreference<EditTextPreference>(getString(R.string.key_add_msg))
        addMsg?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            val msgArray: MutableSet<String> = mangePref.getArray(R.string.key_msg_array.toString())
            add(msgArray, newSub,msgList,R.string.key_msg_array.toString())


        }
        val addWaMsg = findPreference<EditTextPreference>(getString(R.string.key_add_msg_wa))
        addWaMsg?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            val msgWaArray: MutableSet<String> = mangePref.getArray(R.string.key_msg_array_wa.toString())
            add(msgWaArray, newSub,waList,R.string.key_msg_array_wa.toString())

        }
    }

    private fun add(
        Array: MutableSet<String>,
        newSub: String,list: ListPreference,listKey:String
    ) = if (Array.contains(newSub)) {
        Toast.makeText(
            requireContext(), "This is already exists, please select from below",
            Toast.LENGTH_SHORT
        ).show()

        false   // false: reject the new value.
    } else {
        Array.add(newSub)
        mangePref.addToPref(Array, listKey  )
        mangePref.fillListPreference(list, Array)
        true// true: accept the new value.
    }


}

