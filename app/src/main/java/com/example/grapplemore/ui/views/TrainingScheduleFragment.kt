package com.example.grapplemore.ui.views


import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.TrainingScheduleBinding


class TrainingScheduleFragment: Fragment(R.layout.training_schedule) {

    private var fragmentBinding: TrainingScheduleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TrainingScheduleBinding.bind(view)
        fragmentBinding = binding


    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}