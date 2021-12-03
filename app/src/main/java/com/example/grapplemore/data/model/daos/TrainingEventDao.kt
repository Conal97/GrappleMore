package com.example.grapplemore.data.model.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.grapplemore.data.model.entities.TrainingEvent

@Dao
interface TrainingEventDao {

    // Add or update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTrainingEvent(trainingEvent: TrainingEvent)

    // Delete
    @Delete
    suspend fun deleteTrainingEvent(trainingEvent: TrainingEvent)

    // Get all
    @Query("select * from TrainingEvent where fireBaseKey =:fireBaseKey order by unixStartTime ASC")
    fun getAllTrainingEvents(fireBaseKey: String): LiveData<List<TrainingEvent>>

    // Get all upcoming training events
    @Query("select * from TrainingEvent where fireBaseKey =:fireBaseKey and unixStartTime > :currentDateTime order by unixStartTime ASC")
    fun getUpcomingTrainingEvents(fireBaseKey: String, currentDateTime: Long): LiveData<List<TrainingEvent>>

    // Get all previous training events
    @Query("select * from TrainingEvent where fireBaseKey =:fireBaseKey and unixStartTime < :currentDateTime order by unixStartTime ASC")
    fun getPreviousTrainingEvents(fireBaseKey: String, currentDateTime: Long): LiveData<List<TrainingEvent>>

}