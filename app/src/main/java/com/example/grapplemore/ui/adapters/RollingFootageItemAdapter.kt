package com.example.grapplemore.ui.adapters

import android.content.Context
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.RollingFootage
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.rolling_footage_item.view.*
import java.io.File

class RollingFootageItemAdapter(
    var items: List<RollingFootage>,
    val context: Context
    // Need to add on delete listener
): RecyclerView.Adapter<RollingFootageItemAdapter.RollingViewHolder>() {

    inner class RollingViewHolder(rollingItem: View): RecyclerView.ViewHolder(rollingItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RollingViewHolder {
        return RollingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rolling_footage_item,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: RollingViewHolder, position: Int) {

        val curRollingFootage = items[position]

        // Setting video
        val videoParse = Uri.parse(curRollingFootage.videoUri)
        //val videoView = holder.itemView.rollingVideoView
//
//        videoView.setVideoURI(videoParse)
//        val mediaController = MediaController(context)
//        videoView.setMediaController(mediaController)
//        mediaController.setAnchorView(videoView)

        val size = Size(200,200)

        val thumbnail = context.contentResolver.loadThumbnail(videoParse,size,null)

        val ivThumb = holder.itemView.ivThumbnail
        ivThumb.setImageBitmap(thumbnail)

        // Set other parameters
        holder.itemView.tvRollingTitle.text = curRollingFootage.title
        holder.itemView.tvRollingDate.text = curRollingFootage.dateAndTime

    }

    override fun getItemCount(): Int {
        return items.size
    }






}