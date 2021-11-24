package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grapplemore.R
import com.example.grapplemore.databinding.RollingFootageHomeBinding
import com.example.grapplemore.ui.adapters.RollingFootageItemAdapter
import com.example.grapplemore.ui.viewModels.RollingFootageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RollingFootageFragment: Fragment(R.layout.rolling_footage_home) {

    // Reference to viewModel
    private val rollingFootageViewModel: RollingFootageViewModel by viewModels()

    // View binding
    private var fragmentBinding: RollingFootageHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RollingFootageHomeBinding.bind(view)
        fragmentBinding = binding

        val adapter = context?.let { RollingFootageItemAdapter(listOf(), it) }

        binding.rvRollingFootage.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRollingFootage.adapter = adapter

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

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}