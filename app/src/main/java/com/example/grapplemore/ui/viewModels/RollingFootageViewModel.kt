package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.RollingFootage
import com.example.grapplemore.data.repositories.RollingFootageRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RollingFootageViewModel @Inject constructor(
    private val rollingFootageRepository: RollingFootageRepository
): ViewModel() {

    // Firebase for user
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // Add video
    fun insertRollingFootage(rollingFootage: RollingFootage){
        viewModelScope.launch {
            rollingFootageRepository.insertRollingFootage(rollingFootage)
        }
    }

    // Delete video
    fun deleteRollingFootage(rollingFootage: RollingFootage){
        viewModelScope.launch {
            rollingFootageRepository.deleteRollingFootage(rollingFootage)
        }
    }

    // Get rolling footage
    fun getRollingFootage(): LiveData<List<RollingFootage>>{
        return rollingFootageRepository.getRollingFootage(fireBaseKey)
    }
}