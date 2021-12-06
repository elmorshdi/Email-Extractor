package com.elmorshdi.extractor.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.adapter.AlarmAdapter
import com.elmorshdi.extractor.databinding.FragmentListOfAlarmsBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel


class ListOfAlarmsFragment : Fragment() {
    lateinit var binding: FragmentListOfAlarmsBinding

    lateinit var list: List<AlarmDisplayModel>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.frag = this

        val args = ListOfAlarmsFragmentArgs.fromBundle(requireArguments())
        list= args.list.toList()
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
    fun back() {
        val action = ListOfAlarmsFragmentDirections.actionListOfAlarmsFragmentToCalendarFragment()
        binding.backArrow.findNavController().navigate(action)
    }
    fun addAnother(){
        val action = ListOfAlarmsFragmentDirections.actionListOfAlarmsFragmentToAddAlarmFragment(list[0].date)
            binding.addAnother.findNavController().navigate(action)
    }
}