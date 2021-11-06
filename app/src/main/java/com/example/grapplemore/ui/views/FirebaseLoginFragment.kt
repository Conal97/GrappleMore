package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.LoginFragmentBinding
import com.example.grapplemore.databinding.LoginFragmentBinding.bind
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber


class FirebaseLoginFragment : Fragment(R.layout.login_fragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: LoginFragmentBinding

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_UserProfileFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bind(view)

        val inputEmail = binding.etUserName
        val inputPassword = binding.etPassword

        binding.loginButton.setOnClickListener {
            loginUser(inputEmail.toString(), inputPassword.toString())
        }

        binding.signupButton.setOnClickListener {
            createAccount()
        }
    }

    private fun loginUser(inputEmail: String, inputPassword: String) {
        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // sign in successful
                    Timber.d("sign in with email: success")
                    checkLoggedInState()
                    // nav to user profile here
                } else {
                    Timber.d("sign in with email: failure", task.exception)
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun createAccount(){
        NavHostFragment.findNavController(this).navigate(R.id.action_firebaseLoginFragment_to_RegisterUserFragment)
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            Toast.makeText(requireActivity(), "Not a valid login - please sign up", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireActivity(), "Logged in!", Toast.LENGTH_LONG).show()
        }
    }


}