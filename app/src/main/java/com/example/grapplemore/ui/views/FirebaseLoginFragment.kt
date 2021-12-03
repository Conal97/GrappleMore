package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.LoginFragmentBinding
import com.example.grapplemore.ui.viewModels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FirebaseLoginFragment : Fragment(R.layout.login_fragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fragmentLoginBinding: LoginFragmentBinding? = null

    // Reference to viewModel
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null) {
            profileRedirect()
        }
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = LoginFragmentBinding.bind(view)
        fragmentLoginBinding = binding

        binding.loginButton.setOnClickListener {

            val inputEmail = binding.etUserName.text.toString().trim()
            val inputPassword = binding.etPassword.text.toString().trim()

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

                    if (auth.currentUser != null){
                        profileRedirect()
                    }
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

    private fun profileRedirect(){
        userProfileViewModel.getProfile(auth.currentUser?.uid.toString())
        userProfileViewModel.currentProfile.observe(viewLifecycleOwner){
            if (it != null){
                navToProfile()
            }else{
                navToEdit()
            }
        }
    }

    private fun navToProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_UserProfileFragment)
    }

    private fun navToEdit(){
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_userProfileEditCreateFragment)
    }

    private fun createAccount(){
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_firebaseRegisterFragment)
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            Toast.makeText(requireActivity(), "Not a valid login - please sign up", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireActivity(), "Logged in!", Toast.LENGTH_LONG).show()
        }
    }
}