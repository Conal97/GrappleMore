package com.example.grapplemore.ui.views


import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Toast
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
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import kotlinx.datetime.Instant
import timber.log.Timber
import java.time.Clock
import java.util.jar.Manifest


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

            permissionsBuilder(android.Manifest.permission.READ_CALENDAR, android.Manifest.permission.WRITE_CALENDAR)
                .build().send{ result ->
                    if(result.allGranted()){
                        NavHostFragment.findNavController(this)
                            .navigate(R.id.action_trainingScheduleFragment_to_trainingEventAddEditFragment)
                    }
                    else{
                        Toast.makeText(requireActivity(), "Please allow permissions to gain full use of the application", Toast.LENGTH_LONG).show()
                    }
                }
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
        deleteCalendarEvent(trainingEvent)
        trainingEventViewModel.deleteTrainingEvent(trainingEvent)
    }

    override fun editTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.getCurrentTrainingEvent(trainingEvent)
    }

    override fun deletePreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        deleteCalendarEvent(trainingEvent)
        trainingEventViewModel.deleteTrainingEvent(trainingEvent)
    }

    override fun editPreviousTrainingCallBack(trainingEvent: TrainingEvent) {
        trainingEventViewModel.getCurrentTrainingEvent(trainingEvent)
    }

    private fun deleteCalendarEvent(trainingEvent: TrainingEvent){
        // Delete event from calendar
        val eventId = trainingEvent.calendarEventId
        val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
        val rows: Int = requireActivity().contentResolver.delete(deleteUri,null,null)
        Timber.d("Rows deleted: $rows")
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