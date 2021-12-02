package com.example.grapplemore.ui.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.TrainingEvent
import com.example.grapplemore.databinding.TrainingEventAddEditBinding
import com.example.grapplemore.ui.viewModels.TrainingEventViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.training_event_add_edit.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TrainingEventAddEditFragment: Fragment(R.layout.training_event_add_edit), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // Reference to viewModel
    private val trainingEventViewModel: TrainingEventViewModel by activityViewModels()

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TrainingEventAddEditBinding.bind(view)
        fragmentBinding = binding
        var id: Int? = null

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

        val currentTrainingEvent = trainingEventViewModel.currentTrainingEvent.value

        if (currentTrainingEvent != null){
            id = currentTrainingEvent.id

            // Pre-populate fields
            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
            binding.etTrainingTitle.text = currentTrainingEvent.title.toEditable()
            binding.tvDatePicked.text = currentTrainingEvent.startTime.slice((0..9))
            binding.tvStartTime.text = currentTrainingEvent.startTime.slice((11..15))
            binding.tvEndTime.text = currentTrainingEvent.endTime.slice((11..15))

            // Reset to null
            trainingEventViewModel.currentTrainingEvent.value = null
        }

        // Submit on floating action click - move to vm?
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
                // String format for event db table
                val stringDateStart = "$date $start"
                val stringDateEnd = "$date $end"

                // Convert to date format to access day of week
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val localDate = LocalDate.parse(date,formatter)
                val zoneID = ZoneId.systemDefault()
                val dateFormat: Date = Date.from(localDate.atStartOfDay(zoneID).toInstant())

                // Now get day of week
                val dayOfWeek = SimpleDateFormat("EE").format(dateFormat)

                // Convert startTime to ISO time for unix conversion - startTime or endTime better?
                val newFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                val localStartDate = LocalDateTime.parse(stringDateStart,newFormat)
                val isoTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localStartDate)
                val startInstant = Instant.parse(isoTime.replace(":00", "Z"))

                // Now get unix startTime
                val unixStartTime = startInstant.toEpochMilliseconds()
                val currentMoment: Instant = Clock.System.now()
                val dateTimeMillis: Long = currentMoment.toEpochMilliseconds()
                Timber.d("current epoch time is : $dateTimeMillis")

                // Create trainingEvent
                val trainingEvent = TrainingEvent(id, title, unixStartTime,
                    dayOfWeek, stringDateStart, stringDateEnd, fireBaseKey)

                // Call viewModel to update room
                trainingEventViewModel.upsertTrainingEvent(trainingEvent)

                // Navigate
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_trainingEventAddEditFragment_to_trainingScheduleFragment)
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
        hour = c.get(Calendar.HOUR_OF_DAY)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Set the date
        saveDay = dayOfMonth
        saveMonth = month + 1
        saveYear = year
        tvDatePicked.text = String.format("%02d/%02d/%d", saveDay, saveMonth, saveYear)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        // Set start/end time & enforce start is less than end
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


