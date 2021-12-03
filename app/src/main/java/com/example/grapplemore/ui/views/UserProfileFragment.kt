package com.example.grapplemore.ui.views


import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.grapplemore.R
import com.example.grapplemore.databinding.UserProfileFragmentBinding
import com.example.grapplemore.ui.viewModels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.user_profile_fragment) {

    // Reference to viewModel
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    // Firebase for user authentication
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // View binding
    private var fragmentBinding: UserProfileFragmentBinding? = null

    // Mapping for belt colour
    private var beltMap = mapOf(
        "White" to "@drawable/whitebelt",
        "Blue" to "@drawable/bluebelt",
        "Purple" to "@drawable/purplebelt",
        "Brown" to "@drawable/brownbelt",
        "Black" to "@drawable/blackbelt"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = UserProfileFragmentBinding.bind(view)
        fragmentBinding = binding

        // View binding vars
        val usernameTv = binding.tvUserName
        val profileImageView = binding.profilePictureView
        val beltImageView = binding.beltImageView
        val academyTv = binding.academyProfileTv
        val weightCategoryTv = binding.weightCatTv
        val winsDrawsLossTv = binding.winDrawLossTv

        binding.editProfileBtn.setOnClickListener{
            navToEditProfile()
        }

        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            navToLogin()
        }

        // Populate the users profile or display default values if they have not made one yet
        userProfileViewModel.getProfile(fireBaseKey)
        userProfileViewModel.currentProfile.observe(viewLifecycleOwner){
            // User has a profile -> display it
            if (it != null) {
                // Set the username
                usernameTv.text = it.userName

                // Set the profile picture
                Glide.with(requireActivity())
                    .load(Uri.parse(it.profileImageUri))
                    .override(200,150)
                    .centerCrop()
                    .into(profileImageView)

                // Set the belt
                val belt = beltMap[it.beltColour]
                val beltImg = resources.getIdentifier(belt, "drawable", requireActivity().packageName )
                beltImageView.setImageResource(beltImg)

                // Set the academy
                academyTv.text = " Academy: ${it.userAcademy}"

                // Set the weight category
                when (it.weight) {
                    in (1..57) -> weightCategoryTv.text = "Weight Category: Rooster"
                    in (58..64) -> weightCategoryTv.text = "Weight Category: Light Feather"
                    in (65..70) -> weightCategoryTv.text =  "Weight Category: Feather"
                    in (71..76) -> weightCategoryTv.text = "Weight Category: Light"
                    in (77..82) -> weightCategoryTv.text = "Weight Category: Middle"
                    in (82..88) -> weightCategoryTv.text = "Weight Category: Medium Heavy"
                    in (88..94) -> weightCategoryTv.text = "Weight Category: Heavy"
                    in (95..100) -> weightCategoryTv.text = "Weight Category: Super Heavy"
                    in (101..200) -> weightCategoryTv.text ="Weight Category: Ultra Heavy"
                }

                // Set wins, draws, losses
                winsDrawsLossTv.text = "Wins: ${it.wins} | Draws: ${it.draws} | Losses: ${it.losses}"
            }
            else {
                binding.editProfileBtn.text = "Create profile"
            }
        }

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun navToEditProfile() {
        NavHostFragment.findNavController(this).navigate(R.id.action_UserProfileFragment_to_userProfileEditCreateFragment)
    }

    private fun navToLogin(){
        NavHostFragment.findNavController(this).navigate(R.id.action_UserProfileFragment_to_firebaseLoginFragment)
    }
}