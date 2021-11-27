package com.example.grapplemore.ui.views

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.TrainingScheduleBinding
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TrainingScheduleFragment: Fragment(R.layout.training_schedule) {

    private var fragmentBinding: TrainingScheduleBinding? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TrainingScheduleBinding.bind(view)
        fragmentBinding = binding

        val compactCalendarView = binding.compactcalendarView

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY)

        val local = LocalDate.parse("27-11-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val unix = local.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

        val event1 = Event(Color.GREEN, unix)
        compactCalendarView.addEvent(event1)


        compactCalendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener{

            override fun onDayClick(dateClicked: Date?) {
                Toast.makeText(requireActivity(), "Date clicked was $dateClicked", Toast.LENGTH_SHORT).show()
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                Toast.makeText(requireActivity(), "First day is $firstDayOfNewMonth", Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}