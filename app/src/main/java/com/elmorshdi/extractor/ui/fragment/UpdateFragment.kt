package com.elmorshdi.extractor.ui.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.databinding.FragmentUpdateBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.db.Date
import com.elmorshdi.extractor.db.Time
import com.elmorshdi.extractor.other.ManageAlarms
import com.elmorshdi.extractor.other.Utility
import com.elmorshdi.extractor.other.Utility.getCalendar
import com.elmorshdi.extractor.other.Utility.getID
import com.elmorshdi.extractor.other.Utility.timeText
import com.elmorshdi.extractor.ui.viewModels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    @Inject
    lateinit var viewModel: AlarmViewModel
    lateinit var date: Date
    lateinit var timePicked: Time
    private lateinit var model: AlarmDisplayModel
    lateinit var  calendar :Calendar
    private lateinit var binding: FragmentUpdateBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar= Calendar.getInstance()
        val args = UpdateFragmentArgs.fromBundle(requireArguments())
        binding.activity = this
        binding.utility=Utility
        model = args.alarm

        date=model.date
        timePicked=model.time
        binding.alarm = model

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
                model.title=binding.edTitle.text.toString()
                model.summary=binding.edSummary.text.toString()
                model.note=binding.edNote.text.toString()
                model.time=timePicked
                val c =getCalendar(timePicked,date)
                if (c.before(calendar)){
                    Toast.makeText(requireContext(), "The chosen Time is past", Toast.LENGTH_SHORT)
                        .show()
                    setTimeBu()
                }else{
                    send.sendToSystem(model, requireContext())
                    viewModel.updateAlarm(model)
                    clear()
                }



            }
        }

    }

    fun deleteAlarm() {
        val time = binding.edTime.text
        val title = binding.edTitle.text
        val summary = binding.edSummary.text
        val note = binding.edNote.hint
        when {
            title.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Title", Toast.LENGTH_SHORT).show()
            }
            summary.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Summary", Toast.LENGTH_SHORT).show()

            }
            time.equals(null) -> {
                Toast.makeText(requireContext(), "Please Choose the time", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val send = ManageAlarms()
                val id = getID(d = date, t= timePicked)

                val alarmViewModel = AlarmDisplayModel(
                    date = date, title = title.toString(),
                    summary = summary.toString(), time = timePicked, done = false,
                    id = id,
                    note = note.toString()
                )
                send.cancelAlarm(alarmViewModel, requireContext())
                viewModel.deleteAlarm(alarmViewModel.id)
                clear()
            }
        }

    }

    fun back() {
        val action = UpdateFragmentDirections.actionUpdateFragmentToCalendarFragment()
        binding.backArrow.findNavController().navigate(action)
    }

    fun addNewAlarm() {

        val action = UpdateFragmentDirections.actionUpdateFragmentToAddAlarmFragment(date)
        binding.buAdd.findNavController().navigate(action)
    }

    private fun clear() {
        binding.edTime.hint = "Choose the time"
        binding.edTime.text= null

        binding.edNote.text.clear()
        binding.edSummary.text.clear()
        binding.edTitle.text.clear()

    }

    fun setTimeBu() {

        TimePickerDialog(
            context, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                timePicked = Time(hour, minute)
                binding.edTime.text = timeText(timePicked)

            }, calendar.time.hours,
            calendar.time.minutes, false
        ).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }


 }