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
abstract class AppDatabase: RoomDatabase() {

    abstract val UserDao: UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // If the database already exists, retain that version, otherwise build the database.
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}