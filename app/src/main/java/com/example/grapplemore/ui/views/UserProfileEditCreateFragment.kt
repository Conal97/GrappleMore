package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.EditCreateProfileFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileEditCreateFragment: Fragment(R.layout.edit_create_profile_fragment) {

    // Setup variables
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fragmentBinding: EditCreateProfileFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EditCreateProfileFragmentBinding.bind(view)
        fragmentBinding = binding
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }


}