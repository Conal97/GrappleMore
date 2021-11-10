package com.example.grapplemore.data.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile_table")
data class UserProfileEntity(

    @PrimaryKey (autoGenerate = true)
    val userProfileId: Int,

    var fireBaseKey: String?,
    var userName: String?,
    var userAcademy: String?,
    var profileImageUri: String?,
    var weight: Int?,
    var compsAttended: Int = 0,
    var wins: Int = 0,
    var draws: Int = 0,
    var losses: Int = 0
)