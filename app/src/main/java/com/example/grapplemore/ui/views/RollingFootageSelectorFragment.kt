package com.example.grapplemore.ui.views

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.RollingFootage
import com.example.grapplemore.utils.Constants.REQUEST_VIDEO_SELECT
import com.example.grapplemore.utils.Constants.REQUEST_VIDEO_CAPTURE
import com.example.grapplemore.databinding.FootageAdderBinding
import com.example.grapplemore.ui.viewModels.RollingFootageViewModel
import com.google.android.gms.common.wrappers.PackageManagerWrapper
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.footage_adder.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RollingFootageSelectorFragment: Fragment(R.layout.footage_adder) {

    // Reference to viewModel
    private val rollingFootageViewModel: RollingFootageViewModel by activityViewModels()

    // View Binding
    private var fragmentBinding: FootageAdderBinding? = null

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // Global variables for getting uri
    private var videoUri: Uri? = null
    private var uriText: String = ""
    lateinit var greenCheckSelect: ImageView
    lateinit var greenCheckVideoCapture: ImageView
    var id: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FootageAdderBinding.bind(view)
        fragmentBinding = binding
        greenCheckSelect = binding.greenCheckVideoSelect
        greenCheckVideoCapture = binding.greenCheckVideoCapture

        val currentFootage = rollingFootageViewModel.currentRollingFootage.value

        if (currentFootage != null) {

            id = currentFootage.id

            // Pre-populate the title
            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
            binding.etRollingTitle.text = currentFootage.title.toEditable()

            // Preset the video so user doesn't have to re-pick
            uriText = currentFootage.videoUri
            greenCheckSelect.visibility =View.VISIBLE

            // Reset to null
            rollingFootageViewModel.currentRollingFootage.value = null
        }

        // Handling video selection
        binding.footageSelect.setOnClickListener{
            permReqLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }

        // Handling video capture
        binding.footageCapture.setOnClickListener {
            captureReqLauncher.launch(Manifest.permission.CAMERA)
        }

        // Create footage entry in room db - vm?
        binding.createFootageFloat.setOnClickListener {

            val title = binding.etRollingTitle.text.toString()
            val sdf = SimpleDateFormat("HH:mm | dd/MM/yyyy")
            val timestamp = sdf.format(Date())

            if (title.isEmpty() || uriText.isEmpty()
                || timestamp.isEmpty() || fireBaseKey.isEmpty()){
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT)
            }
            else{
                val rollingFootage = RollingFootage(id, title, uriText, timestamp, fireBaseKey)
                rollingFootageViewModel.insertRollingFootage(rollingFootage)
                NavHostFragment.findNavController(this).
                navigate(R.id.action_rollingFootageSelectorFragment_to_rollingFootageFragment)
            }
        }
    }

    // Activity result event triggered when permission is granted
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_SELECT) {
            videoUri = data?.data
            if(videoUri != null){
                uriText = videoUri.toString()
                greenCheckSelect.visibility = View.VISIBLE
                context?.contentResolver?.takePersistableUriPermission(videoUri!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_CAPTURE){
            videoUri = data?.data
            if(videoUri != null){
                uriText = videoUri.toString()
                greenCheckVideoCapture.visibility = View.VISIBLE
            }
        }
    }

    private val captureReqLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
        } else {
            Timber.d("Permission: denied, please allow access to be able to use the app")
            Toast.makeText(requireActivity(), "Permission: denied, please allow access to be able to use the app fully",
                Toast.LENGTH_LONG).show()
        }
    }

    // For handling video select permission -> persistent uri permission to have permanent access
    private val permReqLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            Timber.d("Permission: granted")
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_VIDEO_SELECT)
        } else {
            Timber.d("Permission: denied, please allow access to be able to use the app")
            Toast.makeText(requireActivity(), "Permission: denied, please allow access to be able to use the app fully",
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}

