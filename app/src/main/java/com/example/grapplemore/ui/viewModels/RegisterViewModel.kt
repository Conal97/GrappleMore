package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.grapplemore.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
    ): ViewModel() {

}