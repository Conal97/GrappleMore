package com.example.grapplemore.ui.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    // ViewBinding and init some view variables
    private var fragmentBinding: EditCreateProfileFragmentBinding? = null
    private var beltColour: String = ""
    private var imageUri: Uri? = null
    lateinit var greenCheck: ImageView
    private var uriText: String = ""
    val REQUEST_CODE = 100

    // Todo on start func that checks if user already has profile -> change btn text to 'edit profile'


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EditCreateProfileFragmentBinding.bind(view)
        fragmentBinding = binding
        greenCheck = binding.greenCheck

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

            // do null check here -> for ints

            var fireBaseKey = auth.currentUser?.uid.toString()
            var username = binding.userNameEt.toString()
            var academy = binding.academyEt.toString()
            var weight = binding.weightEt.toString().toInt()
            var compsAttended = binding.compsAttendedEt.toString().toInt()
            var wins = binding.winsEt.toString().toInt()
            var draws = binding.drawsEt.toString().toInt()
            var losses = binding.lossesEt.toString().toInt()


        }

    }

    // Activity result event triggered when permission is called
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            imageUri = data?.data
            if (imageUri != null) {
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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
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