package com.example.grapplemore.ui.views

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.grapplemore.R
import com.example.grapplemore.databinding.UserProfileFragmentBinding
import com.example.grapplemore.ui.viewModels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {

    // Reference to viewModel
    private val userProfileViewModel: UserProfileViewModel by viewModels()

    // Firebase for user authentication
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // View binding and init view variables
    private var fragmentBinding: UserProfileFragmentBinding? = null
    private var username = ""
    private var profilePic = ""
    private var beltColour = ""
    private var academy = ""
    private var weight = ""
    private var wins = null
    private var draws = null
    private var losses = null

    // Maps
    // For belt
    var beltMap = mapOf(
        "white" to "R.drawable.whitebelt.png",
        "blue" to "R.drawable.bluebelt.png",
        "purple" to "R.drawable.purplebelt.png",
        "brownbelt" to "R.drawable.brownbelt.png",
        "blackbelt" to "R.drawable.blackbelt.png"
    )

    // For weight
    var weightCategory = mapOf(
        (1..57) to "Rooster",
        (58..64) to "Light Feather",
        (65..70) to "Feather",
        (71..76) to "Light",
        (77..82) to "Middle",
        (82..88) to "Medium Heavy",
        (88..94) to "Heavy",
        (95..100) to "Super Heavy",
        (100..200) to "Ultra Heavy"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = UserProfileFragmentBinding.bind(view)
        fragmentBinding = binding

        // View binding vars
        var usernameTv = binding.tvUserName
        var profileImageView = binding.profilePictureView
        var beltImageView = binding.beltImageView
        var academyTv = binding.academyProfileTv
        var weightCategoryTv = binding.weightCatTv
        var winsDrawsLossTv = binding.winDrawLossTv

        binding.editProfileBtn.setOnClickListener{
            navToEditProfile()
        }

        // Observers
        // Populate the users profile or display default values if they have not made one yet
        userProfileViewModel.getProfile(fireBaseKey)
        userProfileViewModel.currentProfile.observe(viewLifecycleOwner, Observer {it ->
            // User has a profile -> display it
            if (it != null) {
                usernameTv.text = it.userName

                Glide.with(requireActivity())
                    .load(Uri.parse(it.profileImageUri))
                    .override(200,150)
                    .centerCrop()
                    .into(profileImageView)
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun navToEditProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_UserProfileFragment_to_userProfileEditCreateFragment)
    }
}