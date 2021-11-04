package com.example.grapplemore.ui.viewModels

import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grapplemore.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){

    // Access data from the userRepository
    val users = userRepository.getAllUsers()


    // variables for user verification


    // Function triggered when the login button is clicked, via binding
    fun loginButton(inputUsername:EditText, inputPassword:EditText){

    }

    fun signUp(){
        // may need to implement full data binding for testing purposes
        // i.e. navigation successful
    }
}