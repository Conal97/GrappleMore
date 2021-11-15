package com.example.grapplemore.ui.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.EditCreateProfileFragmentBinding
import com.example.grapplemore.ui.viewModels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserProfileEditCreateFragment: Fragment(R.layout.edit_create_profile_fragment),
    AdapterView.OnItemSelectedListener {

    // Reference to viewModel
    private val userProfileViewModel: UserProfileViewModel by viewModels()

    // Firebase for user authentication
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // ViewBinding and init some view variables
    private var fragmentBinding: EditCreateProfileFragmentBinding? = null
    private var beltColour: String = ""
    private var imageUri: Uri? = null
    lateinit var greenCheck: ImageView
    private var uriText: String = ""
    val REQUEST_CODE = 100

    // todo pre-populate fields if user already has profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EditCreateProfileFragmentBinding.bind(view)
        fragmentBinding = binding
        greenCheck = binding.greenCheck

        // Variables to save the user profile
        val username = binding.userNameEt
        val academy = binding.academyEt
        val weight = binding.weightEt
        val compsAttended = binding.compsAttendedEt
        val wins = binding.winsEt
        val draws = binding.drawsEt
        val losses = binding.lossesEt

        // Handling the drop down menu widget (spinner)
        val spinner: Spinner = binding.beltSpinner

        // Create an array adapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireActivity(), R.array.belt_array, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                //Apply the adapter to the spinner
                spinner.adapter = adapter
                // Get the selected item
                spinner.onItemSelectedListener = this
            }

        // Handling the image button
        binding.profilePictureBtn.setOnClickListener {
            permReqLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Handling the submission button
        binding.submitProfileBtn.setOnClickListener {

            // Null check
            if (fireBaseKey.isEmpty() || username.equals(null) || academy.equals(null)
                    || uriText.isEmpty() || beltColour.isEmpty() || weight.equals(null) || compsAttended.equals(null)
                    || wins.equals(null) || draws.equals(null) || losses.equals(null)) {

                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()

            } else {

                // Enforce weight limit
                if(weight.text.toString().toInt() > 200) {
                    Toast.makeText(requireActivity(), "Weight exceeds maximum limit", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Create or update profile
                    userProfileViewModel.createOrUpdateProfile(
                        fireBaseKey, username.text.toString(), academy.text.toString(),
                        uriText, beltColour ,weight.text.toString().toInt(), compsAttended.text.toString().toInt(),
                        wins.text.toString().toInt(), draws.text.toString().toInt(), losses.text.toString().toInt()
                    )
                }
            }
        }

        // Observers
        // Change text view depending on whether user already has associated profile
        userProfileViewModel.getProfile(fireBaseKey)
        userProfileViewModel.currentProfile.observe(viewLifecycleOwner, Observer {it ->
            if (it != null) {
                binding.submitProfileBtn.text = "Edit Profile"
            }
        })

        // Navigation
        userProfileViewModel.navigate.observe(viewLifecycleOwner, Observer {
            hasFinished -> if (hasFinished == true ) {
                NavHostFragment.findNavController(this).navigate(R.id.action_userProfileEditCreateFragment_to_UserProfileFragment)
                userProfileViewModel.doneNav()
            }
        })

        // Status message
        userProfileViewModel.message.observe(viewLifecycleOwner, Observer { it ->
            it.getContentIfNotHandled()?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Activity result event triggered when permission is called
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            imageUri = data?.data
            if (imageUri != null) {
                // Once user has selected an image, make the green check visible
                greenCheck.visibility = View.VISIBLE
                uriText = imageUri.toString()
            }
        }
    }

    // Release binding
    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    // For handling permissions
    private val permReqLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Timber.d("Permission: granted")
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "image/*"
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE)
        } else {
            Timber.d("Permission: denied")
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val belt = parent?.getItemAtPosition(position)
        beltColour = belt.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}