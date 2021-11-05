package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.grapplemore.R
import com.example.grapplemore.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class FirebaseRegisterFragment: Fragment(R.layout.register_fragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RegisterFragmentBinding.bind(view)

    }
}

