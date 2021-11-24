package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import android.widget.SearchView
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
import timber.log.Timber

@AndroidEntryPoint
class TechniquesArchiveFragment: Fragment(R.layout.techniques_archive),
    ArchiveItemAdapter.callBackInterface,
    ArchiveItemAdapter.deleteCallBack {

    lateinit var searchView: SearchView

    // Reference to viewModel
    private val archiveEntryViewModel: ArchiveEntryViewModel by activityViewModels()

    // Firebase
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireBaseKey = auth.currentUser?.uid.toString()

    // View binding
    private var fragmentBinding: TechniquesArchiveBinding? = null
    private var category = "All"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = TechniquesArchiveBinding.bind(view)
        fragmentBinding = binding

        val adapter = context?.let { ArchiveItemAdapter(listOf(), this, this, it) }

        binding.rvArchiveItems.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvArchiveItems.adapter = adapter

        // Search bar
        searchView = binding.archiveSearchBar

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            // Don't care about submitting the query
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            // Search as query is input
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    val searchQuery = "%$query%"
                    if (category != "All") {
                        archiveEntryViewModel.getByTitleAndCategory(fireBaseKey, category, searchQuery).
                        observe(viewLifecycleOwner, Observer {
                            adapter?.items = it
                            adapter?.notifyDataSetChanged()
                        })
                    }
                    else {
                        archiveEntryViewModel.getByTitle(fireBaseKey, searchQuery).
                        observe(viewLifecycleOwner, Observer {
                            adapter?.items = it
                            adapter?.notifyDataSetChanged()
                        })
                    }
                }
                return true
            }
        })

        // Default: display all entries on opening
        archiveEntryViewModel.getAllUserEntries(fireBaseKey).observe(viewLifecycleOwner, Observer {
            adapter?.items = it
            adapter?.notifyDataSetChanged()
        })

        // Filter by radio button
        binding.radioAll.setOnClickListener {
            if (binding.radioAll.isChecked){
                category = binding.radioAll.text.toString()
                archiveEntryViewModel.getAllUserEntries(fireBaseKey).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioClassNotes.setOnClickListener {
            if (binding.radioClassNotes.isChecked){
                category = binding.radioClassNotes.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioSubmissions.setOnClickListener {
            if (binding.radioSubmissions.isChecked){
                category = binding.radioSubmissions.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioPosition.setOnClickListener {
            if (binding.radioPosition.isChecked){
                category = binding.radioPosition.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioEscape.setOnClickListener {
            if (binding.radioEscape.isChecked){
                category = binding.radioEscape.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioSweepPass.setOnClickListener {
            if (binding.radioSweepPass.isChecked){
                category = binding.radioSweepPass.text.toString()
                archiveEntryViewModel.getByCategory(fireBaseKey, category).observe(viewLifecycleOwner, Observer {
                    adapter?.items = it
                    adapter?.notifyDataSetChanged()
                })
            }
        }

        binding.radioTakedownThrow.setOnClickListener {
            if (binding.radioTakedownThrow.isChecked){
                category = binding.radioTakedownThrow.text.toString()
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
    }

    override fun deleteEntryCallBack(archiveEntry: ArchiveEntry) {
        archiveEntryViewModel.deleteArchiveEntry(archiveEntry)
    }
}