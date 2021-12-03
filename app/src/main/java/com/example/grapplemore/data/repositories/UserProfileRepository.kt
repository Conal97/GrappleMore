package com.example.grapplemore.data.repositories


import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.UserProfileDao
import com.example.grapplemore.data.model.entities.UserProfileEntity
import javax.inject.Inject

// Repository implements the functions defined in the dao interface, injection through dagger
class UserProfileRepository @Inject constructor (
    private val userProfileDao: UserProfileDao
    ) {

    suspend fun upsertProfile(profile: UserProfileEntity){
        return userProfileDao.upsertProfile(profile)
    }

    suspend fun getUserProfile(firebaseKey: String): UserProfileEntity{
        return userProfileDao.getUserProfile(firebaseKey)
    }

}