package com.example.grapplemore.ui.viewModels
//
//import android.util.Log
//import android.widget.EditText
//import android.widget.Toast
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.grapplemore.data.repositories.UserRepository
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import timber.log.Timber
//import javax.inject.Inject
//
//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    private val userRepository: UserRepository
//): ViewModel(){
//
//    // LiveData for this viewModel, accessed via dataBinding
//
//    val username = MutableLiveData<String?>()
//
//
//    val password = MutableLiveData<String?>()
//
//    // Scope for coroutines
//    private val uiScope = CoroutineScope(Dispatchers.Main)
//
//    // Access data from the userRepository
//    val users = userRepository.getAllUsers()
//
//    // Function triggered when the login button is clicked, via binding
//    fun login() {
//        if (username.value == null || password.value == null) {
//            _errorToast.value = true
//        }
//        uiScope.launch {
//            // Attempt to access user from user repository
//            val currentUser = userRepository.getUser(username.value.toString())
//
//            // If user exists
//            if (currentUser != null) {
//
//                // Check for valid password
//                if (currentUser.password == password.value) {
//                    // password is valid -> reset edit text fields and nav to user profile
//                    username.value = null
//                    password.value = null
//                } else {
//                    // password is invalid -> display error toast
//                    _errorToastInvalidPassword.value = true
//                }
//            } else {
//                // username is invalid -> display error toast
//                _errorToastUsername.value = true
//            }
//
//
//        }
//    }
//    fun createAccount(){
//        _navigateToRegisterFragment.value = true
//    }
//
//    //----- Navigation observers -----
//    private val _navigateToRegisterFragment = MutableLiveData<Boolean>()
//    val navigateToRegisterFragment: LiveData<Boolean>
//        get() = _navigateToRegisterFragment
//
//    private val _navigateToUserProfile = MutableLiveData<Boolean>()
//    val navigateToUserProfile: LiveData<Boolean>
//        get() = _navigateToUserProfile
//
//    fun doneNavToRegister() {
//        _navigateToRegisterFragment.value = false
//    }
//
//    fun doneNavToUserProfile() {
//        _navigateToUserProfile.value = false
//    }
//
//    // ---------------
//
//    //---- Toast observers -----
//    private val _errorToast = MutableLiveData<Boolean>()
//    val errorToast: LiveData<Boolean>
//        get() = _errorToast
//
//    private val _errorToastUsername = MutableLiveData<Boolean>()
//    val errorToastUsername: LiveData<Boolean>
//        get() = _errorToastUsername
//
//    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()
//    val errorToastInvalidPassword: LiveData<Boolean>
//        get() = _errorToastInvalidPassword
//
//    fun doneToast() {
//        _errorToast.value = false
//        Timber.i("Complete")
//    }
//
//    fun doneToastErrorUsername() {
//        _errorToastUsername.value = false
//        Timber.i("Complete")
//    }
//
//    fun doneToastInvalidPassword() {
//        _errorToastInvalidPassword.value = false
//        Timber.i("Complete")
//    }
//
//    // ---------------
//
//}