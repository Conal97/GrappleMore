package com.example.grapplemore.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.databinding.ArchiveEntryBinding
import com.example.grapplemore.ui.adapters.ArchiveItemAdapter
import com.example.grapplemore.ui.viewModels.ArchiveEntryViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.archive_entry.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ArchiveEntryFragment: Fragment(R.layout.archive_entry){

    // Reference to viewModel
    private val archiveEntryViewModel: ArchiveEntryViewModel by activityViewModels()

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
        var category = ""

        val currentEntry = archiveEntryViewModel.currentID.value
        Timber.d("id is: $currentEntry")

//        if(currentEntry != null) {
//            Timber.d("Archive entry id is: ${archiveEntryViewModel.currentArchiveEntry.value?.id}")
//
//            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
//
//            // If currentEntry not null, display it
//            binding.etArchiveEntryTitle.text = currentEntry.title.toEditable()
//            binding.etEntryBody.text = currentEntry.content.toEditable()
//
//            when(currentEntry.category){
//                "Class Note" -> binding.radioClassNotes.isChecked = true
//                "Submission" -> binding.radioSubmissions.isChecked = true
//                "Position" -> binding.radioPosition.isChecked = true
//                "Sweep/Pass" -> binding.radioSweepPass.isChecked = true
//                "Takedown/Throw" -> binding.radioTakedownThrow.isChecked = true
//                "Escape" -> binding.radioEscape.isChecked = true
//            }
//            // Reset to null
//            archiveEntryViewModel.currentArchiveEntry.value = null
//
//        }



        // Floating action button to submit entry
        binding.createEntryFloat.setOnClickListener {

            val title = binding.etArchiveEntryTitle.text.toString()
            val content = binding.etEntryBody.text.toString()
            val sdf = SimpleDateFormat("dd/M")
            val timestamp = sdf.format(Date())

            if( title.isEmpty() || category.isEmpty() || content.isEmpty() ||
                timestamp.isEmpty() || fireBaseKey.isEmpty()) {
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                val archiveEntry = ArchiveEntry(id = null, title, category, content, timestamp, fireBaseKey)
                archiveEntryViewModel.upsertEntry(archiveEntry)
                Toast.makeText(requireActivity(), "Entry added", Toast.LENGTH_SHORT).show()
            }
        }

        // Handling radio buttons
        binding.radioClassNotes.setOnClickListener {
            if (binding.radioClassNotes.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_orange_border)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_orange_border)
                category = radioClassNotes.text.toString()
            }
        }
        binding.radioSubmissions.setOnClickListener {
            if (binding.radioSubmissions.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_submission_background)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_submission_background)
                category = radioSubmissions.text.toString()
            }
        }
        binding.radioPosition.setOnClickListener {
            if (binding.radioPosition.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_position_green)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_position_green)
                category = radioPosition.text.toString()
            }
        }
        binding.radioSweepPass.setOnClickListener {
            if (binding.radioSweepPass.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_sweep_pass_purple)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_sweep_pass_purple)
                category = radioSweepPass.text.toString()
            }
        }
        binding.radioTakedownThrow.setOnClickListener {
            if (binding.radioTakedownThrow.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_takedownthrow_yello)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_takedownthrow_yello)
                category = radioTakedownThrow.text.toString()
            }
        }
        binding.radioEscape.setOnClickListener {
            if (binding.radioEscape.isChecked){
                binding.etArchiveEntryTitle.background = resources.getDrawable(R.drawable.rounded_escape_blue)
                binding.etEntryBody.background = resources.getDrawable(R.drawable.rounded_escape_blue)
                category = radioEscape.text.toString()
            }
        }

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}


