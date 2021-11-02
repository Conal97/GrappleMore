package com.example.grapplemore.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.grapplemore.data.model.daos.UserDao
import com.example.grapplemore.data.model.entities.UserEntity


@Database(

    entities = [
        UserEntity::class
    ],

    // Update when adding tables to database
    version = 1

)
// Dagger-Hilt handles the boilerplate associated with the database
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

}