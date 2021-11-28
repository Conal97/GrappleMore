package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
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

    // Current dateTime in milliseconds
    val currentMoment: Instant = Clock.System.now()
    val dateTimeMillis: Long = currentMoment.toEpochMilliseconds()

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
    fun getUpcomingTrainingEvents(): LiveData<List<TrainingEvent>>{
        return trainingEventRepository.getUpcomingTrainingEvents(fireBaseKey, dateTimeMillis)
    }

    // Get previous training events
    fun getPreviousTrainingEvents(): LiveData<List<TrainingEvent>>{
        return trainingEventRepository.getPreviousTrainingEvents(fireBaseKey, dateTimeMillis)
    }
}