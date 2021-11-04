package com.example.grapplemore.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grapplemore.R
import com.example.grapplemore.databinding.LoginFragmentBinding
import com.example.grapplemore.ui.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    // View binding
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    // Access viewModel for to execute business logic
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_RegisterUserFragment)
        }

        binding.loginButton.setOnClickListener {
            if (viewModel.loginButton(binding.etUserName, binding.etPassword)) {
                findNavController().navigate(R.id.action_loginFragment_to_UserProfileFragment)
            }
            else {
                val toast = Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}