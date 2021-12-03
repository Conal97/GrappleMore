package com.example.grapplemore.data.model.daos


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.grapplemore.data.model.entities.UserProfileEntity

@Dao
interface UserProfileDao {

    // Upsert user profile
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: UserProfileEntity)

    // Get a single userProfile by firebaseKey
    @Query("select * from user_profile_table where fireBaseKey = :fireBaseKey")
    suspend fun getUserProfile(fireBaseKey: String): UserProfileEntity
}