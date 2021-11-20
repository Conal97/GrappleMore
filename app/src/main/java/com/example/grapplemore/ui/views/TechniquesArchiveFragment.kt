package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.databinding.TechniquesArchiveBinding
import com.example.grapplemore.ui.adapters.ArchiveItemAdapter
import com.example.grapplemore.ui.viewModels.ArchiveEntryViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechniquesArchiveFragment: Fragment(R.layout.techniques_archive), ArchiveItemAdapter.callBackInterface, ArchiveItemAdapter.deleteCallBack {

    // Reference to viewModel
    private val archiveEntryViewModel: ArchiveEntryViewModel by activityViewModels()

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // View binding
    private var fragmentBinding: TechniquesArchiveBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TechniquesArchiveBinding.bind(view)
        fragmentBinding = binding

        val adapter = context?.let { ArchiveItemAdapter(listOf(), this, this, it) }

        binding.rvArchiveItems.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvArchiveItems.adapter = adapter

        // Default to display all entries on opening
        archiveEntryViewModel.getAllUserEntries(fireBaseKey).observe(viewLifecycleOwner, Observer {
            adapter?.items = it
            adapter?.notifyDataSetChanged()
        })

        // Filter by radio button
        binding.radioClassNotes.setOnClickListener {
            if (binding.radioClassNotes.isChecked){
                val category = binding.radioClassNotes.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.archiveFloatingActionButton.setOnClickListener{
            navigate()
        }
    }

    fun navigate(){
        NavHostFragment.findNavController(this).navigate(R.id.action_techniquesArchiveFragment_to_archiveEntryFragment)
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun passResultsCallback(archiveEntry: ArchiveEntry) {
        archiveEntryViewModel.getCurrentEntry(archiveEntry)
        val id = archiveEntryViewModel.currentArchiveEntry.value?.id
    }

    override fun deleteEntryCallBack(archiveEntry: ArchiveEntry) {
        archiveEntryViewModel.deleteArchiveEntry(archiveEntry)
    }
}