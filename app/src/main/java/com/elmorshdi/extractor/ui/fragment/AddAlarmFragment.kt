package com.elmorshdi.extractor.ui.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.databinding.AddAlarmFragmentBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.db.Time
import com.elmorshdi.extractor.other.ManageAlarms
import com.elmorshdi.extractor.ui.viewModels.AddAlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddAlarmFragment : Fragment() {
    @Inject
    lateinit var viewModel: AddAlarmViewModel
    lateinit var date: String
    lateinit var model: AlarmDisplayModel
    private lateinit var binding: AddAlarmFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = AddAlarmFragmentArgs.fromBundle(requireArguments())
        date = args.date
        binding.activity = this

        if (args.model != null) {
            model = args.model!!
            binding.alarm = model
            binding.newAlarm.visibility = View.GONE
            binding.buAdd.visibility = View.GONE
            binding.updateLiner.visibility = View.VISIBLE
            binding.buUpdate.visibility = View.VISIBLE
            binding.buDelete.visibility = View.VISIBLE
            binding.buAddU.visibility = View.VISIBLE

            Log.d("tag",model.time)
        }
        binding.buUpdate.setOnClickListener(View.OnClickListener {
            updateAlarm()
        })
        binding.buAddU.setOnClickListener(View.OnClickListener {
            addAlarm()
        })
        binding.buDelete.setOnClickListener(View.OnClickListener {
            deleteAlarm()
        })
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

                val alarmViewModel = AlarmDisplayModel(
                    date = date,
                    title = binding.edTitle.text.toString(),
                    summary = binding.edSummary.text.toString(),
                    time = binding.edTime.text.toString(),
                    done = false,
                    id = id,
                    note = binding.edNote.text.toString(),
                    roomId = model.roomId
                )
                send.sendToSystem(alarmViewModel, requireContext())
                viewModel.updateAlarm(alarmViewModel)
                Log.d("tag", ":title:${alarmViewModel.title}+" +
                        "summary:${alarmViewModel.summary}+date:${alarmViewModel.date}" +
                        "+id:${alarmViewModel.id}+note:${alarmViewModel.note}+")

                clear()
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
                val id = send.getID(date = date, time = time.toString())

                val alarmViewModel = AlarmDisplayModel(
                    date = date, title = title.toString(),
                    summary = summary.toString(), time = time.toString(), done = false,
                    id = id,
                    note = note.toString()
                )
                send.cancelAlarm(alarmViewModel, requireContext())
                viewModel.deleteAlarm(alarmViewModel)
                clear()
            }
        }

    }

    fun back() {
        val action = AddAlarmFragmentDirections.actionAddAlarmFragmentToCalendarFragment()
        binding.backArrow.findNavController().navigate(action)
    }

    fun addAlarm() {
        val time = binding.edTime.text
        val title = binding.edTitle.text
        val summary = binding.edSummary.text
        val note = binding.edNote.text
        when {
            title.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Title", Toast.LENGTH_SHORT).show()
            }
            summary.isEmpty() -> {
                Toast.makeText(requireContext(), "Write The Summary", Toast.LENGTH_SHORT).show()

            }
            time.isEmpty() -> {
                Toast.makeText(requireContext(), "Please Choose the time", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val send = ManageAlarms()
                val id = send.getID(date = date, time = time.toString())
                val alarmViewModel = AlarmDisplayModel(
                    date = date,
                    title = title.toString(),
                    summary = summary.toString(),
                    time = time.toString(),
                    done = false,
                    id = id,
                    note = note.toString()
                )
                Log.d("tag", "add:title:${alarmViewModel.title}+" +
                        "summary:${alarmViewModel.summary}+date:${alarmViewModel.date}" +
                        "+id:${alarmViewModel.id}+note:${alarmViewModel.note}+")

                send.sendToSystem(alarmViewModel, requireContext())
                viewModel.insertAlarm(alarmViewModel)
                clear()
            }
        }

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
                val time = Time(hour, minute)
                binding.edTime.text = time.timeText
            }, 9,
            30, false
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddAlarmFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


}