package com.example.grapplemore.ui.views


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import kotlinx.datetime.Instant
import java.time.Clock


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

        refreshFragment(requireActivity())

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

        // Current dateTime in milliseconds
        val currentMoment: Instant = kotlinx.datetime.Clock.System.now()
        val dateTimeMillis: Long = currentMoment.toEpochMilliseconds()

        trainingEventViewModel.getUpcomingTrainingEvents(dateTimeMillis).observe(viewLifecycleOwner, Observer {
            adapterUpcoming?.items = it
            adapterUpcoming?.notifyDataSetChanged()
        })

        trainingEventViewModel.getPreviousTrainingEvents(dateTimeMillis).observe(viewLifecycleOwner, Observer {
            adapterPrevious?.items = it
            adapterPrevious?.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun deleteTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.deleteTrainingEvent(trainingEvent)
    }

    override fun editTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.getCurrentTrainingEvent(trainingEvent)
    }

    override fun deletePreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.deleteTrainingEvent(trainingEvent)
    }

    override fun editPreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.getCurrentTrainingEvent(trainingEvent)
    }

    fun refreshFragment(context: Context){
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let{
                val currentFragment = fragmentManager.findFragmentById(R.id.trainingScheduleContainer)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }
}