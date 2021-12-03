package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.UserProfileEntity
import com.example.grapplemore.data.repositories.UserProfileRepository
import com.example.grapplemore.utils.Event
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    // Live data to access user profile outside viewModel scope
    private var _currentProfile = MutableLiveData<UserProfileEntity?>()
    val currentProfile: MutableLiveData<UserProfileEntity?>
        get() = _currentProfile

    // Get profile via fireBaseKey
    fun getProfile(fireBaseKey: String){
        viewModelScope.launch {
            val profile = userProfileRepository.getUserProfile(fireBaseKey)
            getCurrentProfile(profile)
        }
    }

    // Set profile
    private fun getCurrentProfile(profile: UserProfileEntity){
        currentProfile.value = profile
    }

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
    fun createOrUpdateProfile(profile: UserProfileEntity) {

        viewModelScope.launch {
            // Upsert into db
            userProfileRepository.upsertProfile(profile)
            getCurrentProfile(profile)
            _navigate.value = true
        }
    }
}