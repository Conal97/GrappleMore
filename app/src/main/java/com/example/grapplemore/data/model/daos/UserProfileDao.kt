package com.example.grapplemore.data.model.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.grapplemore.data.model.entities.UserProfileEntity

@Dao
interface UserProfileDao {

    // Add a new user profile
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProfile(profile: UserProfileEntity)

    // Update user profile
    @Update
    suspend fun updateProfile(profile: UserProfileEntity)

    // Get a single userProfile by firebaseKey
    @Query("select * from user_profile_table where fireBaseKey = :fireBaseKey")
    fun getUserProfile(fireBaseKey: String): LiveData<UserProfileEntity>

    // Get all current userNames
    @Query("select userName from user_profile_table")
    fun getAllUserNames(): LiveData<List<String>>

}