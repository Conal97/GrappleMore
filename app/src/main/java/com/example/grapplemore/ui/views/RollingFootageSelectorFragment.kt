package com.example.grapplemore.ui.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.RollingFootage
import com.example.grapplemore.utils.Constants.REQUEST_CODE
import com.example.grapplemore.databinding.FootageAdderBinding
import com.example.grapplemore.ui.viewModels.RollingFootageViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RollingFootageSelectorFragment: Fragment(R.layout.footage_adder) {

    // Reference to viewModel
    private val rollingFootageViewModel: RollingFootageViewModel by viewModels()

    // View Binding
    private var fragmentBinding: FootageAdderBinding? = null

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // Global variables for getting uri
    private var videoUri: Uri? = null
    private var uriText: String = ""
    lateinit var greenCheck: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FootageAdderBinding.bind(view)
        fragmentBinding = binding
        greenCheck = binding.greenCheckVideo

        // Handling video selection
        binding.footageSelect.setOnClickListener{
            permReqLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        binding.createFootageFloat.setOnClickListener {

            val title = binding.etRollingTitle.text.toString()
            val sdf = SimpleDateFormat("HH:mm | dd/M")
            val timestamp = sdf.format(Date())

            if (title.isEmpty() || uriText.isEmpty()
                || timestamp.isEmpty() || fireBaseKey.isEmpty()){
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT)
            }
            else{
                val rollingFootage = RollingFootage(null, title, uriText, timestamp, fireBaseKey)
                rollingFootageViewModel.insertRollingFootage(rollingFootage)
                NavHostFragment.findNavController(this).
                navigate(R.id.action_rollingFootageSelectorFragment_to_rollingFootageFragment)
            }
        }
    }

    // Activity result event triggered when permission is granted
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            videoUri = data?.data
            if(videoUri != null){
                uriText = videoUri.toString()
                greenCheck.visibility = View.VISIBLE
            }
        }
    }

    // For handling permissions
    private val permReqLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Timber.d("Permission: granted")
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_CODE)
        } else {
            Timber.d("Permission: denied, please allow access to be able to use the app")
            Toast.makeText(requireActivity(), "Permission: denied, please allow access to be able to use the app",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}