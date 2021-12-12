package com.elmorshdi.extractor.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.databinding.FragmentCalendarBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.db.Date
import com.elmorshdi.extractor.other.Utility.getCalendar
import com.elmorshdi.extractor.ui.viewModels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject











@AndroidEntryPoint
class CalendarFragment : Fragment()   {
    @Inject
    lateinit var viewModel: AlarmViewModel
    lateinit var binding:FragmentCalendarBinding
    private lateinit var events: MutableList<EventDay>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  list = runBlocking (Dispatchers.IO){  viewModel.getAllAlarmAsync().await() }

        events = ArrayList()
        dote(list)

        binding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
                intentTo(clickedDayCalendar  )
            }
        })


    }

    private fun intentTo(
        d: Calendar,
     ) {
        binding.progress.visibility = View.VISIBLE
      val date=Date(d.get(YEAR), d.get(MONTH),   d.get(DAY_OF_MONTH) )
       val dateAlarms= arrayListOf<AlarmDisplayModel>()
       val list = runBlocking (Dispatchers.IO){  viewModel.getAllAlarmAsync().await() }

        Log.d("tag", "flies:id:${list}")

         for (a in list){
             if (a.date==date)dateAlarms.add(a)

         }

        when {
            dateAlarms.isEmpty() -> {
                Log.d("tag", "dateAlarms.isEmpty()")

                val cal=getCalendar()
                cal.add(DAY_OF_MONTH, -1)
                if (d.before(cal)) {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), "The chosen date is past", Toast.LENGTH_SHORT)
                        .show()
                }else{
                val action = CalendarFragmentDirections.actionCalendarFragmentToAddAlarmFragment(date)
                    binding.root.findNavController().navigate(action)}
                Log.d("tag", "flies:id:${date}")

            }
            dateAlarms.size==1 -> {
                Log.d("tag", "dateAlarms.size==1")

                if (dateAlarms[0].done){
                    Log.d("tag", "dateAlarms.size==1,done")

                    val action = CalendarFragmentDirections.actionCalendarFragmentToAlarmViewFragment(dateAlarms[0])
                    binding.root.findNavController().navigate(action)
                }else{
                    Log.d("tag", "dateAlarms.size==1,false")

                    val action = CalendarFragmentDirections.actionCalendarFragmentToUpdateFragment( dateAlarms[0])
                    binding.root.findNavController().navigate(action)
                }

            }
            dateAlarms.size>1 -> {
                Log.d("tag", "dateAlarms.size>1 ")

                val array: Array<AlarmDisplayModel> = dateAlarms.toTypedArray()

                val action = CalendarFragmentDirections.actionCalendarFragmentToListOfAlarmsFragment(array)
                binding.root.findNavController().navigate(action)
            }
        }


    }

    private fun dote(list: List<AlarmDisplayModel>) {
        for (a in list) {
            val cal =getCalendar(a.date)
            if (a.done){
                events.add(EventDay(cal, R.drawable.ic_alarm_done,Color.GREEN))
            }else{
                events.add(EventDay(cal, R.drawable.ic_alarm,Color.RED))
            }

        }
        binding.calendarView.setEvents(events)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }

}