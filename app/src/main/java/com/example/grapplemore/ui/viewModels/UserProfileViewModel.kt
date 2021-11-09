package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grapplemore.data.repositories.UserProfileRepository
import com.example.grapplemore.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    // Wrap event class for toast messaging
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    // Scope for coroutines
    private val uiScope = CoroutineScope(Dispatchers.Main)

    // Function for creating a new user profile
    fun addProfile(fireBaseKey: String, userName: String, academy: String,
                imageUri: String, weight: Int, compsAttended: Int,
                wins: Int, draws: Int, losses: Int) {
    }

    // Function for updating an existing user profile
    fun updateProfile(fireBaseKey: String, userName: String, academy: String,
                      imageUri: String, weight: Int, compsAttended: Int,
                      wins: Int, draws: Int, losses: Int) {
    }

    // Check if logged in user has a profile or not
    fun profileExists(fireBaseKey: String): Boolean {
        val profile = userProfileRepository.getUserProfile(fireBaseKey)
        return profile.value != null
    }


    // Check if userName exists
    fun userNameExists(userName: String): Boolean {
        val listOfUserNames = userProfileRepository.getAllUserNames()
        if (listOfUserNames.value?.contains(userName)!!) {
            return false
        }
        return true
    }

}