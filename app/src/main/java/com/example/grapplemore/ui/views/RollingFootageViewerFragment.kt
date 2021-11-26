package com.example.grapplemore.ui.views

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.grapplemore.R
import com.example.grapplemore.databinding.RollingViewerBinding
import com.example.grapplemore.ui.viewModels.RollingFootageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RollingFootageViewerFragment: Fragment(R.layout.rolling_viewer) {

    // Ref to viewModel -> will change this to shared
    private val rollingFootageViewModel: RollingFootageViewModel by activityViewModels()

    // View Binding
    private var fragmentBinding: RollingViewerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RollingViewerBinding.bind(view)
        fragmentBinding = binding

        val currentFootage = rollingFootageViewModel.currentRollingFootage.value

        // Setting video
        if (currentFootage != null) {
            val videoParse = Uri.parse(currentFootage.videoUri)
            val videoView = binding.videoViewer
            videoView.setVideoURI(videoParse)
            val mediaController = MediaController(context)
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(videoView)
            videoView.start()

            // Reset to null
            rollingFootageViewModel.currentRollingFootage.value = null
        }

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}