package com.example.grapplemore.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class Entry (
    @PrimaryKey (autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val category: String,
    val content: String,
    val timestamp: Long,
    val fireBaseKey: String
)