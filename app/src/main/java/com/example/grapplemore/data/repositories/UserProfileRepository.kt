package com.example.grapplemore.data.repositories


import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.UserProfileDao
import com.example.grapplemore.data.model.entities.UserProfileEntity
import javax.inject.Inject

// Repository implements the functions defined in the dao interface, injection through dagger
class UserProfileRepository @Inject constructor (
    private val userProfileDao: UserProfileDao
    ) {

    suspend fun addProfile(profile: UserProfileEntity) {
        return userProfileDao.addProfile(profile)
    }

    suspend fun updateProfile(profile: UserProfileEntity){
        return userProfileDao.updateProfile(profile)
    }

    fun getUserProfile(firebaseKey: String): LiveData<UserProfileEntity>{
        return userProfileDao.getUserProfile(firebaseKey)
    }

    fun getAllUserNames(): LiveData<List<String>>{
        return userProfileDao.getAllUserNames()
    }
}