package com.example.grapplemore.ui.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.TrainingEventAddEditBinding
import kotlinx.android.synthetic.main.archive_item.*
import kotlinx.android.synthetic.main.training_event_add_edit.*
import java.util.*

class TrainingEventAddEditFragment: Fragment(R.layout.training_event_add_edit), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var fragmentBinding: TrainingEventAddEditBinding? = null

    var day = 0
    var month = 0
    var year = 0
    var minute = 0
    var hour = 0

    var saveDay = 0
    var saveMonth = 0
    var saveYear = 0

    var startClicked = false

    var startMinute = 0
    var startHour = 0

    var endMinute = 0
    var endHour = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TrainingEventAddEditBinding.bind(view)
        fragmentBinding = binding

        // Date picker
        binding.dateSelect.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireActivity(),this, year, month, day).show()
        }
        // Start time picker
        binding.startSelect.setOnClickListener {
            startClicked = true
            getDateTimeCalendar()
            TimePickerDialog(requireActivity(), this, hour, minute, true).show()
        }
        // End time picker
        binding.endSelect.setOnClickListener {
            startClicked = false
            getDateTimeCalendar()
            TimePickerDialog(requireActivity(), this, hour, minute, true).show()
        }

        // Submit on floating action click
        binding.createTrainingEventFloat.setOnClickListener {

            val title = binding.etTrainingTitle.text.toString()
            val date = binding.tvDatePicked.text.toString()
            val start = binding.tvStartTime.text.toString()
            val end = binding.tvEndTime.text.toString()

            // Null check
            if( title.isEmpty() || date.isEmpty() || start.isEmpty() || end.isEmpty()){
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                // todo Convert to dateTime formats and get in unix date format
                Toast.makeText(requireActivity(), "All filled", Toast.LENGTH_SHORT).show()

            }

        }

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun getDateTimeCalendar(){
        // Get current time and date
        val c = Calendar.getInstance()
        day = c.get(Calendar.DAY_OF_MONTH)
        month = c.get(Calendar.MONTH)
        year = c.get(Calendar.YEAR)
        minute = c.get(Calendar.MINUTE)
        hour = c.get(Calendar.HOUR)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Set the date
        saveDay = dayOfMonth
        saveMonth = dayOfMonth
        saveYear = year
        tvDatePicked.text = String.format("%02d/%02d/%d", saveDay, saveMonth, saveYear)
        //tvDatePicked.text = "Date is: $saveDay/$saveMonth/$saveYear"
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // Set start/end time & enforce start < end
        if(startClicked) {
            startHour = hourOfDay
            startMinute = minute
            tvStartTime.text = String.format("%02d:%02d", startHour, startMinute)
            tvEndTime.text = " "
            endHour = 0
            endMinute = 0
        }
        else {
            endHour = hourOfDay
            endMinute = minute
            if ((startHour*1000) +startMinute < (endHour*1000) +endMinute){
                tvEndTime.text = String.format("%02d:%02d", endHour, endMinute)
            } else{
                Toast.makeText(requireActivity(), "Please select an end time after the start time", Toast.LENGTH_SHORT).show()
            }
        }
    }
}