package com.example.grapplemore.ui.views
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Observer
//import androidx.navigation.fragment.NavHostFragment
//import androidx.navigation.fragment.findNavController
//import com.example.grapplemore.R
//import com.example.grapplemore.databinding.LoginFragmentBinding
//import com.example.grapplemore.ui.viewModels.LoginViewModel
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class LoginFragment : Fragment(R.layout.login_fragment) {
//
////    // Create instance of the view model as a late init
////    private val loginViewModel: LoginViewModel by viewModels()
////
////    // inflate the fragment with the corresponding layout file
////    override fun onCreateView(
////        inflater: LayoutInflater,
////        container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
////            inflater,
////            R.layout.login_fragment,
////            container,
////            false
////        )
////
////        // binding the variable declared in the XML with the view model reference
////        binding.myLoginViewModel = loginViewModel
////
////
////        //------- Navigation implementation of observers in LoginViewModel
////        loginViewModel.navigateToRegisterFragment.observe(viewLifecycleOwner, Observer {
////            hasFinished ->
////            if (hasFinished == true) {
////                /// Navigate and alert observer
////                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_RegisterUserFragment)
////                loginViewModel.doneNavToRegister()
////            }
////        })
////
////        loginViewModel.navigateToUserProfile.observe(viewLifecycleOwner, Observer {
////                hasFinished ->
////            if (hasFinished == true) {
////                // Navigate and alert observer
////                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_RegisterUserFragment)
////                loginViewModel.doneNavToUserProfile()
////            }
////        })
////        // --------------------------------
////
////        //-------- Error toasts implementation of observers in LoginViewModel
////        loginViewModel.errorToast.observe(viewLifecycleOwner, Observer {
////            hasError->
////            if(hasError==true){
////                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
////                loginViewModel.doneToast()
////            }
////        })
////
////        loginViewModel.errorToastUsername .observe(viewLifecycleOwner, Observer { hasError->
////            if(hasError==true){
////                Toast.makeText(requireContext(), "User doesn't exist, please sign up!", Toast.LENGTH_SHORT).show()
////                loginViewModel.doneToastErrorUsername()
////            }
////        })
////
////        loginViewModel.errorToastInvalidPassword.observe(viewLifecycleOwner, Observer { hasError->
////            if(hasError==true){
////                Toast.makeText(requireContext(), "Invalid password", Toast.LENGTH_SHORT).show()
////                loginViewModel.doneToastInvalidPassword()
////            }
////        })
////        // ---------------------------------
////
////        // return binding.root
////        return binding.root
////    }
//}
