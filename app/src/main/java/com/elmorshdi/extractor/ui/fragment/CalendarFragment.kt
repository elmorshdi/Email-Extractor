package com.elmorshdi.extractor.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.databinding.FragmentCalendarBinding
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.db.Date
import com.elmorshdi.extractor.repository.MainRepository
import com.elmorshdi.extractor.ui.viewModels.CalendarViewModel
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
  class CalendarFragment : Fragment()   {
    @Inject
    lateinit var viewModel: CalendarViewModel
    @Inject
    lateinit var mainRepository: MainRepository
lateinit var binding:FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val list = runBlocking (Dispatchers.IO){  viewModel.getAllAlarmAsync().await() }
         renderCalender(list , R.color.colorAccent)

        binding.Calender.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
             moveTo(date, widget  )

         })

    }

    private fun moveTo(
        d: CalendarDay,
        widget: MaterialCalendarView
     ) {
        binding.progress.visibility = View.VISIBLE
      val date=Date(day =d.day, month = d.month, year = d.year ).dateText
       var dateAlarms= arrayListOf<AlarmDisplayModel>()
        val list= runBlocking (Dispatchers.IO){  viewModel.getAllAlarmAsync().await() }
        Log.d("tag", "flies:id:${list}")

         for (a in list){
             if (a.date==date)dateAlarms.add(a)
         }

            if (dateAlarms.isEmpty()){
                val action = CalendarFragmentDirections.actionCalendarFragmentToAddAlarmFragment(date)
                widget.findNavController().navigate(action)
            }else if (dateAlarms.size==1){
                if (dateAlarms[0].done){
                    val action = CalendarFragmentDirections.actionCalendarFragmentToAlarmViewFragment(dateAlarms[0])
                    widget.findNavController().navigate(action)
                }
                val action = CalendarFragmentDirections.actionCalendarFragmentToAddAlarmFragment(date,dateAlarms[0])
                widget.findNavController().navigate(action)
            }else if (dateAlarms.size>1){
                val array: Array<AlarmDisplayModel> = dateAlarms.toTypedArray()

                val action = CalendarFragmentDirections.actionCalendarFragmentToListOfAlarmesFragment(array)
                widget.findNavController().navigate(action)
            }


    }

    private fun renderCalender(list:List<AlarmDisplayModel>, color: Int){
        val doneDate= arrayListOf<CalendarDay>()
        val nextDate= arrayListOf<CalendarDay>()

        for (d in list) {
            val c = d.date.split("/")
            doneDate.add(
                CalendarDay(c[2].toInt(), c[1].toInt(), c[0].toInt())
            )

        }

     val eventDecorator1=EventDecorator(color,doneDate)
     Log.d("tag", "room:id:${list.size}")
     binding.Calender.addDecorators(eventDecorator1)
   }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }
    class EventDecorator(private val color: Int, dates: Collection<CalendarDay>) :
        DayViewDecorator {
         private val dates =HashSet(dates)
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(DotSpan(5F, color))
        }


    }
}