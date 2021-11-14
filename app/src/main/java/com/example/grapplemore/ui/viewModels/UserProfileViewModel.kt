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
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    // Live data to access user profile outside viewModel scope
    private var _currentProfile = MutableLiveData<UserProfileEntity?>()
    val currentProfile: MutableLiveData<UserProfileEntity?>
        get() = _currentProfile

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

            val profile = UserProfileEntity(fireBaseKey, userName, academy,
            imageUri, weight, compsAttended, wins, draws, losses)

            val existingProfile = userProfileRepository.getUserProfile(fireBaseKey)

            // Check if profile already exists
            if (existingProfile != null) {
                // If it exists -> update entry
                userProfileRepository.updateProfile(profile)
                statusMessage.value = Event("Profile updated")
                _navigate.value = true
            }
            // Create a new profile
            else {
                userProfileRepository.addProfile(profile)
                statusMessage.value = Event("Profile created")
                _navigate.value = true
            }
        }
    }

    // Function to implement user profile observing
    fun getProfile(fireBaseKey: String){
        viewModelScope.launch {
            val profile = userProfileRepository.getUserProfile(fireBaseKey)
            if (profile!=null) {
                _currentProfile.value = profile
            }
        }
    }

}