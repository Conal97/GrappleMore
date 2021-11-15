package com.example.grapplemore.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grapplemore.data.model.daos.UserProfileDao
import com.example.grapplemore.data.model.entities.UserProfileEntity


@Database(
    entities = [
        UserProfileEntity::class
    ],

    // Update when adding tables to database
    version = 4
)
// Dagger-Hilt handles the boilerplate associated with the database
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserProfileDao(): UserProfileDao

}