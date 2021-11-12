package com.example.grapplemore.ui.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber


class FirebaseLoginFragment : Fragment(R.layout.login_fragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fragmentLoginBinding: LoginFragmentBinding? = null


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null) {
            navToProfile()
        }
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = LoginFragmentBinding.bind(view)
        fragmentLoginBinding = binding

        binding.loginButton.setOnClickListener {

            val inputEmail = binding.etUserName.text.toString()
            val inputPassword = binding.etPassword.text.toString()

            if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                loginUser(inputEmail, inputPassword)
            }
            else {
                Toast.makeText(requireActivity(), "Please fill in email and password fields", Toast.LENGTH_LONG).show()
            }
        }

        binding.signupButton.setOnClickListener {
            createAccount()
        }

        binding.resetPasswordButton.setOnClickListener {
            val inputEmail = binding.etUserName.text.toString()
            resetPassword(inputEmail)
        }
    }

    override fun onDestroyView() {
        fragmentLoginBinding = null
        super.onDestroyView()
    }

    private fun loginUser(inputEmail: String, inputPassword: String) {
        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // sign in successful
                    Timber.d("sign in with email: success")
                    checkLoggedInState()
                    navToProfile()
                } else {
                    Timber.d("sign in with email: failure", task.exception)
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resetPassword(inputEmail: String) {

        if (inputEmail.isNotEmpty()) {
            // Send a reset password email to a user who has forgotten their password
            auth.sendPasswordResetEmail(inputEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Timber.d("Email sent", task.exception)
                        Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(requireActivity(), "Please fill in email field", Toast.LENGTH_LONG).show()
        }


    }

    private fun navToProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_UserProfileFragment)
    }

    private fun createAccount(){
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_firebaseRegisterFragment)
    }

    // Change this?
    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            Toast.makeText(requireActivity(), "Not a valid login - please sign up", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireActivity(), "Logged in!", Toast.LENGTH_LONG).show()
        }
    }


}