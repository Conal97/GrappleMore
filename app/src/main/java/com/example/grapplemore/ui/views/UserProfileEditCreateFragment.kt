package com.example.grapplemore.ui.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.UserProfileEntity
import com.example.grapplemore.databinding.EditCreateProfileFragmentBinding
import com.example.grapplemore.ui.viewModels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import com.example.grapplemore.utils.Constants.REQUEST_PHOTO_SELECT
import kotlinx.android.synthetic.main.edit_create_profile_fragment.*

@AndroidEntryPoint
class UserProfileEditCreateFragment: Fragment(R.layout.edit_create_profile_fragment),
    AdapterView.OnItemSelectedListener {

    // Firebase for user
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // Reference to viewModel
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    // ViewBinding and init some view variables
    private var fragmentBinding: EditCreateProfileFragmentBinding? = null
    private var beltColour: String = ""
    private var imageUri: Uri? = null
    lateinit var greenCheck: ImageView
    private var uriText: String = ""
    var id: Int? = null

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

        val currentProfile = userProfileViewModel.currentProfile.value

        // Pre-populate edit screen with current values
        if(currentProfile != null){

            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

            // Edit text fields
            username.text = currentProfile.userName?.toEditable()
            academy.text = currentProfile.userAcademy?.toEditable()
            weight.text = currentProfile.weight?.toString()?.toEditable()
            compsAttended.text = currentProfile.compsAttended.toString().toEditable()
            wins.text = currentProfile.wins.toString().toEditable()
            draws.text = currentProfile.draws.toString().toEditable()
            losses.text = currentProfile.losses.toString().toEditable()

            // Image and belt
            greenCheck.visibility = View.VISIBLE
            uriText = currentProfile.profileImageUri.toString()
            beltColour = currentProfile.beltColour.toString()

            // Button
            binding.submitProfileBtn.text = "Edit Profile"

            userProfileViewModel.currentProfile.value = null
        }

        // Handling the drop down menu widget (spinner)
        val spinner: Spinner = binding.beltSpinner

        // Create an array adapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireActivity(), R.array.belt_array, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                //Apply the adapter to the spinner
                spinner.adapter = adapter

                when(beltColour){
                    "White" -> spinner.setSelection(0)
                    "Blue" -> spinner.setSelection(1)
                    "Purple" -> spinner.setSelection(2)
                    "Brown" -> spinner.setSelection(3)
                    "Black" -> spinner.setSelection(4)
                }

                // Get the selected item
                spinner.onItemSelectedListener = this
            }

        // Handling the image button
        binding.profilePictureBtn.setOnClickListener {
            permReqLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Handling the submission button
        binding.submitProfileBtn.setOnClickListener {

            // Null input check
            if (username.equals(null) || academy.equals(null)
                    || uriText.isEmpty() || beltColour.isEmpty() || weight.equals(null) || compsAttended.equals(null)
                    || wins.equals(null) || draws.equals(null) || losses.equals(null)) {

                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()

            } else {

                // Enforce weight limit
                if(weight.text.toString().toInt() > 200 || weight.text.toString().toInt() < 40) {
                    Toast.makeText(requireActivity(), "Weight entries below 40 and above 200 are not permitted", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Create profile
                    val profile = UserProfileEntity(fireBaseKey, username.text.toString(), academy.text.toString(),
                        uriText, beltColour, weight.text.toString().toInt(), compsAttended.text.toString().toInt(),
                        wins.text.toString().toInt(), draws.text.toString().toInt(), losses.text.toString().toInt())

                    userProfileViewModel.createOrUpdateProfile(profile)

                }
            }
        }

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

    // Activity result event triggered when permission is granted
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO_SELECT) {
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
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_PHOTO_SELECT)
        } else {
            Timber.d("Permission: denied, please allow access to be able to use the app")
            Toast.makeText(requireActivity(), "Permission: denied, please allow access to be able to use the app",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val belt = parent?.getItemAtPosition(position)
        beltColour = belt.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}