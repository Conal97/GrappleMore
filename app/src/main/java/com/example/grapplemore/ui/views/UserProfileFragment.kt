package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.UserProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {

    //Setup variables
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fragmentBinding: UserProfileFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = UserProfileFragmentBinding.bind(view)
        fragmentBinding = binding

        binding.editProfileBtn.setOnClickListener{
            navToEditProfile()
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun navToEditProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_UserProfileFragment_to_userProfileEditCreateFragment)
    }
}