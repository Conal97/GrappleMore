package com.example.grapplemore.ui.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.grapplemore.R
import com.example.grapplemore.databinding.ArchiveEntryBinding
import com.example.grapplemore.databinding.TechniquesArchiveBinding
import com.example.grapplemore.ui.viewModels.ArchiveEntryViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.archive_entry.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ArchiveEntryFragment: Fragment(R.layout.archive_entry) {

    // Reference to viewModel
    private val archiveEntryViewModel: ArchiveEntryViewModel by viewModels()

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // View binding
    private var fragmentBinding: ArchiveEntryBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables") // maybe change to this?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ArchiveEntryBinding.bind(view)
        fragmentBinding = binding

        // Setting entry variables
        val title = binding.etArchiveEntryTitle.text.toString()
        var category = ""
        val content = binding.etEntryBody.text.toString()
        val sdf = SimpleDateFormat("dd/M")
        val timestamp = sdf.format(Date())

        binding.radioClassNotes.setOnClickListener {
            if (binding.radioClassNotes.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_orange_border)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_orange_border)
                category = radioClassNotes.text.toString()
            }
        }

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}


