package com.example.grapplemore.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grapplemore.data.model.daos.EntryDao
import com.example.grapplemore.data.model.daos.UserProfileDao
import com.example.grapplemore.data.model.entities.Entry
import com.example.grapplemore.data.model.entities.UserProfileEntity


@Database(
    entities = [
        UserProfileEntity::class,
        Entry::class
    ],

    // Update when adding tables to database
    version = 5
)
// Dagger-Hilt handles the boilerplate associated with the database
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserProfileDao(): UserProfileDao
    abstract fun getEntryDao(): EntryDao
}