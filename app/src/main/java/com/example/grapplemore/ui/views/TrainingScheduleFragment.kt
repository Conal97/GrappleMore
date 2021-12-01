package com.example.grapplemore.ui.views


import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.TrainingEvent
import com.example.grapplemore.databinding.TrainingScheduleBinding
import com.example.grapplemore.ui.adapters.TrainingEventAdapterPrevious
import com.example.grapplemore.ui.adapters.TrainingEventAdapterUpcoming
import com.example.grapplemore.ui.viewModels.TrainingEventViewModel


class TrainingScheduleFragment: Fragment(R.layout.training_schedule),
    TrainingEventAdapterUpcoming.editTrainingCallBack,
    TrainingEventAdapterUpcoming.deleteTrainingCallBack,
    TrainingEventAdapterPrevious.editPreviousTrainingCallBack,
    TrainingEventAdapterPrevious.deletePreviousTrainingCallBack {

    // Ref to viewModel
    private val trainingEventViewModel: TrainingEventViewModel by activityViewModels()

    private var fragmentBinding: TrainingScheduleBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TrainingScheduleBinding.bind(view)
        fragmentBinding = binding

        // Set the adapters
        val adapterUpcoming = context?.let { TrainingEventAdapterUpcoming(listOf(), this, this, it) }
        val adapterPrevious = context?.let { TrainingEventAdapterPrevious(listOf(), this, this, it) }

        // Set the before and after recycler views
        binding.rvUpcomingTraining.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingTraining.adapter = adapterUpcoming
        binding.rvPreviousTraining.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPreviousTraining.adapter = adapterPrevious

        binding.trainingEventFloatingActionButton.setOnClickListener{
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_trainingScheduleFragment_to_trainingEventAddEditFragment)
        }

        trainingEventViewModel.getUpcomingTrainingEvents().observe(viewLifecycleOwner, Observer {
            adapterUpcoming?.items = it
            adapterUpcoming?.notifyDataSetChanged()
        })

        trainingEventViewModel.getPreviousTrainingEvents().observe(viewLifecycleOwner, Observer {
            adapterPrevious?.items = it
            adapterPrevious?.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun deleteTrainingCallBack(trainingEvent: TrainingEvent) {
        TODO("Not yet implemented")
    }

    override fun editTrainingCallBack(trainingEvent: TrainingEvent) {
        TODO("Not yet implemented")
    }

    override fun deletePreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        TODO("Not yet implemented")
    }

    override fun editPreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        TODO("Not yet implemented")
    }
}