package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import timber.log.Timber

class FirebaseRegisterFragment: Fragment(R.layout.register_fragment) {

    // Access firebase and binding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fragmentRegisterBinding: RegisterFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RegisterFragmentBinding.bind(view)
        fragmentRegisterBinding = binding

        // Onclick lister to execute user registration
        binding.registerButton.setOnClickListener {

            // Get variables from inflated XML
            val passOne: String = binding.etPassword.text.toString()
            val passTwo: String = binding.etPassword2.text.toString()
            val email: String = binding.etUserEmail.text.toString()

            // Check that fields are filled
            if(email.isNotEmpty() && passOne.isNotEmpty() && passTwo.isNotEmpty()) {

                // Verify pass words match
                if(verifyPassword(passOne, passTwo)) {

                    // If they match, register and navigate to user profile
                    registerUser(email, passOne)
                }
            }
            else {
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        fragmentRegisterBinding = null
        super.onDestroyView()
    }

    private fun registerUser(inputEmail: String, inputPassword: String) {

        // Use firebase to register new user
        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Timber.d("user registered")
                    Toast.makeText(requireActivity(), "User registered successfully", Toast.LENGTH_LONG).show()
                    navToProfile()
                }
                // Handle exceptions
                else {
                    try {
                        throw task.exception!!
                    // Catch double registration
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Timber.d("$e")
                        Toast.makeText(requireActivity(), "This email address is already used by another account", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun verifyPassword(inputPassOne: String, inputPassTwo: String): Boolean {

        // Verify input passwords match
        return if (inputPassOne == inputPassTwo) {
            true
        } else {
            // Error message if fail
            Toast.makeText(requireActivity(), "Entered passwords don't match, please try again", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun navToProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseRegisterFragment_to_UserProfileFragment)
    }
}





