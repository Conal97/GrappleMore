package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FirebaseRegisterFragment: Fragment(R.layout.register_fragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RegisterFragmentBinding.bind(view)

        // To do
        // change XML to new layout -> email, pass1, pass2
        // access values and pass to functions, from an onclick listener
        // test it works, also maybe some exception handling in login fragment



    }

    private fun registerUser(inputEmail: String, inputPassword: String) {

        // Use firebase to register new user
        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("user registered")
                    Toast.makeText(requireActivity(), "User registered successfully", Toast.LENGTH_LONG).show()
                }
                // Handle exceptions
                else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        if (inputEmail == e.email) {
                            Timber.d("User with this email already exists")
                            Toast.makeText(requireActivity(), "User registered successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    private fun verifyPassword(inputPassOne: String, inputPassTwo: String): Boolean {

        // Verify input passwords match
        if (inputPassOne == inputPassTwo) {
            return true
        }
        // Error message if fail
        Toast.makeText(requireActivity(), "Entered passwords don't match, please try again", Toast.LENGTH_LONG).show()
        return false
    }
}



