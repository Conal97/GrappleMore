package com.example.grapplemore.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Data class that represents user table in database
@Entity(tableName = "users_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "password_text")
    val password: String
)
