package com.elmorshdi.extractor.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elmorshdi.extractor.adapter.AlarmAdapter
import com.elmorshdi.extractor.databinding.FragmentListOfAlarmsBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel


class ListOfAlarmsFragment : Fragment() {
    lateinit var binding: FragmentListOfAlarmsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ListOfAlarmsFragmentArgs.fromBundle(requireArguments())
        val list: List<AlarmDisplayModel> = args.list.toList()
        Log.d("tag", "listAlarms:id:${list.size}")

        val alarmAdapter= AlarmAdapter(list)
         binding.rvAlarm.adapter = alarmAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListOfAlarmsBinding.inflate(inflater, container, false)

        return binding.root
    }


}