package com.example.grapplemore.data.repositories

import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.TrainingEventDao
import com.example.grapplemore.data.model.entities.TrainingEvent
import javax.inject.Inject

class TrainingEventRepository @Inject constructor(
    private val trainingEventDao: TrainingEventDao
) {

    suspend fun upsertTrainingEvent(trainingEvent: TrainingEvent){
        return trainingEventDao.upsertTrainingEvent(trainingEvent)
    }

    suspend fun deleteTrainingEvent(trainingEvent: TrainingEvent){
        return trainingEventDao.deleteTrainingEvent(trainingEvent)
    }

    fun getAllTrainingEvents(fireBaseKey: String): LiveData<List<TrainingEvent>>{
        return trainingEventDao.getAllTrainingEvents(fireBaseKey)
    }

    fun getUpcomingTrainingEvents(fireBaseKey: String, currentDateTime: Long): LiveData<List<TrainingEvent>>{
        return trainingEventDao.getUpcomingTrainingEvents(fireBaseKey, currentDateTime)
    }

    fun getPreviousTrainingEvents(fireBaseKey: String, currentDateTime: Long): LiveData<List<TrainingEvent>>{
        return trainingEventDao.getPreviousTrainingEvents(fireBaseKey, currentDateTime)
    }

}