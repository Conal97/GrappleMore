package com.example.grapplemore.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val unixStartTime: Long,
    val unixEndTime: Long,
    val calendarEventId: Long?,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val fireBaseKey: String
)
