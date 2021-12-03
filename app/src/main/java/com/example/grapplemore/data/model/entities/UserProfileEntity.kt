package com.example.grapplemore.data.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile_table")
data class UserProfileEntity(

    @PrimaryKey(autoGenerate = false)
    val fireBaseKey: String,

    val userName: String?,
    val userAcademy: String?,
    val profileImageUri: String?,
    val beltColour: String?,
    val weight: Int?,
    val compsAttended: Int = 0,
    val wins: Int = 0,
    val draws: Int = 0,
    val losses: Int = 0,

)