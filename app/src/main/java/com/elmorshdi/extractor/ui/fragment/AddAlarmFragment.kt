package com.elmorshdi.extractor.ui.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.databinding.AddAlarmFragmentBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.db.Date
import com.elmorshdi.extractor.db.Time
import com.elmorshdi.extractor.other.ManageAlarms
import com.elmorshdi.extractor.other.Utility.getCalendar
import com.elmorshdi.extractor.other.Utility.getID
import com.elmorshdi.extractor.other.Utility.timeText
import com.elmorshdi.extractor.ui.viewModels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AddAlarmFragment : Fragment() {
    @Inject
    lateinit var viewModel: AlarmViewModel
    lateinit var date: Date
    lateinit var timeP: Time
     private lateinit var binding: AddAlarmFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = AddAlarmFragmentArgs.fromBundle(requireArguments())
        date = args.date
        binding.activity = this

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
                val id = getID(date, timeP)
                val model=AlarmDisplayModel(date,
                timeP,title.toString(),summary.toString(),
                    note.toString(),false,id)

                val calendar = getCalendar()
                val c=getCalendar(timeP,date)
                if (c.before(calendar)){
                    Toast.makeText(requireContext(), "The chosen Time is past", Toast.LENGTH_SHORT)
                        .show()
                    setTimeBu()
                }else{
                send.sendToSystem(model, requireContext())
                viewModel.insertAlarm(model)
                clear()
                }
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
        val calendar = getCalendar().getTime()
        TimePickerDialog(
            context, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                 timeP = Time(hour, minute)
                binding.edTime.text = timeText(timeP)

            }, calendar.hours,
            calendar.minutes, false
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