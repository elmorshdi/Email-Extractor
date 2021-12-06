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
    @Inject
    lateinit var mangePref: MangePref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        mangePref.fillListPreference(subjectList, mangePref.getArray(R.string.key_subject_array.toString()))

        mangePref.fillListPreference(msgList, mangePref.getArray(R.string.key_msg_array.toString()))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        subjectList = findPreference(getString(R.string.key_select_subject))!!
        msgList = findPreference(getString(R.string.key_select_msg))!!


        val addSubject = findPreference<EditTextPreference>(getString(R.string.key_add_subject))
        addSubject?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            val subjectArray: MutableSet<String> = mangePref.getArray(R.string.key_subject_array.toString())
            if (subjectArray.contains(newSub)) {
                Toast.makeText(
                    requireContext(), "This Subject already exists, please select from below",
                    Toast.LENGTH_SHORT
                ).show()

                false   // false: reject the new value.
            } else {
                subjectArray.add(newSub)
                mangePref.addToPref(subjectArray, R.string.key_subject_array.toString())
                mangePref.fillListPreference(subjectList, subjectArray)

                true// true: accept the new value.
            }

        }
        val addMsg = findPreference<EditTextPreference>(getString(R.string.key_add_msg))
        addMsg?.setOnPreferenceChangeListener { _, newValue ->
            val newSub = newValue as String
            val msgArray: MutableSet<String> = mangePref.getArray(R.string.key_msg_array.toString())
            if (msgArray.contains(newSub)) {
                Toast.makeText(
                    requireContext(), "This Message already exists, please select from below",
                    Toast.LENGTH_SHORT
                ).show()

                false   // false: reject the new value.
            } else {
                msgArray.add(newSub)
                mangePref.addToPref(msgArray, R.string.key_msg_array.toString())
                mangePref.fillListPreference(msgList, msgArray)
                true// true: accept the new value.
            }

        }

    }




}

