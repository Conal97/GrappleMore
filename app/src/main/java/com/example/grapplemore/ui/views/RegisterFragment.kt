package com.example.grapplemore.ui.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.grapplemore.R
import com.example.grapplemore.ui.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.register_fragment) {

    private val viewModel: RegisterViewModel by viewModels()
}