package com.example.grapplemore.ui.viewModels


import android.widget.EditText
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Continuation


class FirebaseLoginViewModel(private val dispatcher: CoroutineDispatcher): ViewModel(), LifecycleObserver {

//    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    @Bindable
//    val inputEmail = MutableLiveData<String?>()
//
//    @Bindable
//    val inputPassword = MutableLiveData<String?>()
//
//    private fun loginUser() {
//        viewModelScope.launch(dispatcher){
//            try {
//                auth.let { login ->
//                    login.signInWithEmailAndPassword(inputEmail.toString(), inputPassword.toString())
//                        .addOnCompleteListener
//
//
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
//    private val _errorToastAuthenticate = MutableLiveData<Boolean>()
//    val errorToastAuthenticate: LiveData<Boolean>
//        get() = _errorToastAuthenticate
//
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
//    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//    }
//
//    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//    }
}




// if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//
//            } catch (e: Exception) {
//
//            }
//        }
//    }