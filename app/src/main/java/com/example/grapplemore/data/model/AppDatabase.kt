package com.example.grapplemore.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grapplemore.data.model.daos.ArchiveEntryDao
import com.example.grapplemore.data.model.daos.RollingFootageDao
//import com.example.grapplemore.data.model.daos.ArchiveEntryDao
import com.example.grapplemore.data.model.daos.UserProfileDao
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.data.model.entities.RollingFootage
//import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.data.model.entities.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        ArchiveEntry::class,
        RollingFootage::class,
    ],

    // Update when adding tables to database
    version = 10,
    exportSchema = false
)
// Dagger-Hilt handles the boilerplate associated with the database
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserProfileDao(): UserProfileDao
    abstract fun getArchiveEntryDao(): ArchiveEntryDao
    abstract fun getRollingFootageDao(): RollingFootageDao
}