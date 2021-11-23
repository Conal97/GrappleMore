package com.example.grapplemore.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RollingFootage(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val videoUri: String,
    val dateAndTime: String,
    val fireBaseKey: String
)
