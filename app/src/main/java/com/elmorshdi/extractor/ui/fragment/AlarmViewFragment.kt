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
import com.elmorshdi.extractor.other.Utility
import com.elmorshdi.extractor.ui.viewModels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmViewFragment : Fragment() {
    @Inject
    lateinit var viewModel: AlarmViewModel
    lateinit var model: AlarmDisplayModel
    private lateinit var binding: FragmentAlarmViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = AlarmViewFragmentArgs.fromBundle(requireArguments())
        binding.utility= Utility

        binding.frag = this
        model = args.alarm
        binding.alarm = model
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentAlarmViewBinding.inflate(inflater, container, false)

        return binding.root
    }
    fun updateAlarm() {


        if(binding.edNote.text.toString()==model.note) {
                Toast.makeText(requireContext(), "The note is not changed", Toast.LENGTH_SHORT).show()
        }
        else {
                val note=binding.edNote.text.toString()
                viewModel.updateNote(note,model.id)
             }


    }
    fun back() {
        val action = AlarmViewFragmentDirections.actionAlarmViewFragmentToCalendarFragment()
        binding.backArrow.findNavController().navigate(action)
    }  fun addNew() {
        val action = AlarmViewFragmentDirections.actionAlarmViewFragmentToAddAlarmFragment(model.date)
        binding.backArrow.findNavController().navigate(action)
    }
}