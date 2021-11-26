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
import com.example.grapplemore.data.model.entities.RollingFootage
import com.example.grapplemore.databinding.RollingFootageHomeBinding
import com.example.grapplemore.ui.adapters.RollingFootageItemAdapter
import com.example.grapplemore.ui.viewModels.RollingFootageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RollingFootageFragment: Fragment(R.layout.rolling_footage_home),
    RollingFootageItemAdapter.deleteItemCallBack,
    RollingFootageItemAdapter.changeViewCallBack {

    // Reference to viewModel
    private val rollingFootageViewModel: RollingFootageViewModel by activityViewModels()

    // View binding
    private var fragmentBinding: RollingFootageHomeBinding? = null
    lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RollingFootageHomeBinding.bind(view)
        fragmentBinding = binding

        val adapter = context?.let { RollingFootageItemAdapter(listOf(),this, this,it) }

        binding.rvRollingFootage.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRollingFootage.adapter = adapter

        // Search bar
        searchView = binding.footageSearchBar
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            // Don't care about submitting the query
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            // Search as query is input
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    val searchQuery = "%$query%"
                        rollingFootageViewModel.getFootageByTitle(searchQuery).
                        observe(viewLifecycleOwner, Observer {
                            adapter?.items = it
                            adapter?.notifyDataSetChanged()
                        })
                    }
                return true
            }
        })

        // Default: display all entries on opening
        rollingFootageViewModel.getRollingFootage().observe(viewLifecycleOwner, Observer {
            adapter?.items = it
            adapter?.notifyDataSetChanged()
        })

        binding.footageFloatingActionButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_rollingFootageFragment_to_rollingFootageSelectorFragment)
        }

    }

    override fun deleteEntryCallBack(rollingFootage: RollingFootage) {
        rollingFootageViewModel.deleteRollingFootage(rollingFootage)
    }

    override fun changeViewCallBack(rollingFootage: RollingFootage) {
        rollingFootageViewModel.getCurrentRollingFootage(rollingFootage)
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}