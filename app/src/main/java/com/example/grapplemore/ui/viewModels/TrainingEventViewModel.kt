package com.example.grapplemore.ui.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.TrainingEvent
import com.example.grapplemore.data.repositories.TrainingEventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class TrainingEventViewModel @Inject constructor(
    private val trainingEventRepository: TrainingEventRepository
): ViewModel(){

    // Firebase for user
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // LIve data to access trainingEvent outside viewModel scope
    private var _currentTrainingEvent = MutableLiveData<TrainingEvent?>()
    val currentTrainingEvent: MutableLiveData<TrainingEvent?>
        get() = _currentTrainingEvent

    fun getCurrentTrainingEvent(trainingEvent: TrainingEvent){
        currentTrainingEvent.value = trainingEvent
    }

    // Add training event
    fun upsertTrainingEvent(trainingEvent: TrainingEvent){
        viewModelScope.launch {
            trainingEventRepository.upsertTrainingEvent(trainingEvent)

        }
    }

    // Delete training event
    fun deleteTrainingEvent(trainingEvent: TrainingEvent){
        viewModelScope.launch {
            trainingEventRepository.deleteTrainingEvent(trainingEvent)
        }
    }

    // Get upcoming training events
    fun getUpcomingTrainingEvents(dateTimeMillis: Long): LiveData<List<TrainingEvent>>{
        return trainingEventRepository.getUpcomingTrainingEvents(fireBaseKey, dateTimeMillis)
    }

    // Get previous training events
    fun getPreviousTrainingEvents(dateTimeMillis: Long): LiveData<List<TrainingEvent>>{
        return trainingEventRepository.getPreviousTrainingEvents(fireBaseKey, dateTimeMillis)
    }
}