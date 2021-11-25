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

        // Setting video
        val contentString = "content://com.android.providers.media.documents/document/video%3A32"
        val videoParse = Uri.parse(contentString)
        val videoView = binding.videoViewer

        videoView.setVideoURI(videoParse)
        val mediaController = MediaController(context)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}