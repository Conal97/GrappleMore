package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.UserProfileEntity
import com.example.grapplemore.data.repositories.UserProfileRepository
import com.example.grapplemore.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    // User profile entity to update database
    private val _userProfile = MutableLiveData<UserProfileEntity>()
    private val userProfile: LiveData<UserProfileEntity> = _userProfile

    // Wrap event class for toast messaging
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    // Handling navigation with live data
    private val _navigate= MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate
    fun doneNav() {
        _navigate.value = false
    }

    // Function for creating a new user profile
    fun createOrUpdateProfile(fireBaseKey: String, userName: String, academy: String,
                imageUri: String, weight: Int, compsAttended: Int,
                wins: Int, draws: Int, losses: Int) {

        viewModelScope.launch {

            // Check all fields have input
            if (fireBaseKey.isEmpty() || userName.isEmpty() || academy.isEmpty()
                || imageUri.isEmpty() || weight.equals(null) || compsAttended.equals(null)
                || wins.equals(null) || draws.equals(null) || losses.equals(null)) {

                // Status message
                statusMessage.value = Event("Please fill all fields")
            }
            else {

                // Catch duplicate username
                if (!userNameExists(userName)) {

                    // Set userProfile entity from inputs
                    userProfile.value!!.fireBaseKey = fireBaseKey
                    userProfile.value!!.userName = userName
                    userProfile.value!!.userAcademy = academy
                    userProfile.value!!.profileImageUri = imageUri
                    userProfile.value!!.weight = weight
                    userProfile.value!!.compsAttended = compsAttended
                    userProfile.value!!.wins = wins
                    userProfile.value!!.draws = draws
                    userProfile.value!!.losses = losses

                    // Check if profile already exists
                    if (profileExists(fireBaseKey)) {
                        // If it exists -> update entry
                        userProfileRepository.updateProfile(userProfile.value!!)
                        statusMessage.value = Event("Profile updated")
                        _navigate.value = true
                    }
                    // Create a new profile
                    else {
                        userProfileRepository.addProfile(userProfile.value!!)
                        statusMessage.value = Event("Profile created")
                        _navigate.value = true
                    }
                } else {
                    // username exists, update status message
                    statusMessage.value = Event("Username already exists, please choose another!")
                }

            }
        }
    }

    // Check if logged in user has a profile or not
    fun profileExists(fireBaseKey: String): Boolean {
        val profile = userProfileRepository.getUserProfile(fireBaseKey)
        return profile.value != null
    }

    // Check if userName exists
    private fun userNameExists(userName: String): Boolean {
        val listOfUserNames = userProfileRepository.getAllUserNames()
        if (listOfUserNames.value?.contains(userName)!!) {
            return true
        }
        return false
    }
}