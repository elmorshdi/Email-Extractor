package com.elmorshdi.extractor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.databinding.FragmentAlarmViewBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.other.ManageAlarms
import com.elmorshdi.extractor.ui.viewModels.AddAlarmViewModel


class AlarmViewFragment : Fragment() {
    lateinit var viewModel: AddAlarmViewModel
    lateinit var date: String
    lateinit var model: AlarmDisplayModel
    private lateinit var binding: FragmentAlarmViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = AlarmViewFragmentArgs.fromBundle(requireArguments())
        binding.frag = this

        model = args.alarm
        binding.alarm = model
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentAlarmViewBinding.inflate(inflater, container, false)

        return binding.root
    }
    fun updateAlarm() {

        when {
            binding.edTitle.text.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Title", Toast.LENGTH_SHORT).show()
            }
            binding.edSummary.text.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Summary", Toast.LENGTH_SHORT).show()

            }
            binding.edTime.text.isEmpty() -> {
                Toast.makeText(requireContext(), "Please Choose the time", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val send = ManageAlarms()
                val id = send.getID(date = date, time = binding.edTime.text.toString())

                model.note=binding.edNote.text.toString()
                viewModel.updateAlarm(model)


            }
        }

    }
    fun back() {
        val action = AlarmViewFragmentDirections.actionAlarmViewFragmentToCalendarFragment()
        binding.backArrow.findNavController().navigate(action)
    }
}