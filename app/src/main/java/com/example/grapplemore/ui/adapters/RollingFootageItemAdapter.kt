package com.example.grapplemore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.RollingFootage
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.rolling_footage_item.view.*

class RollingFootageItemAdapter(
    var items: List<RollingFootage>,
    val context: Context
    // Need to add on delete listener
): RecyclerView.Adapter<RollingFootageItemAdapter.RollingViewHolder>() {

    inner class RollingViewHolder(rollingItem: View): RecyclerView.ViewHolder(rollingItem)

    private var player: SimpleExoPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RollingViewHolder {
        return RollingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rolling_footage_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RollingViewHolder, position: Int) {

        val curRollingFootage = items[position]
        val videoUri = curRollingFootage.videoUri
        // Init ExoPlayer
        initializePlayer(holder, videoUri)

        holder.itemView.tvRollingTitle.text = curRollingFootage.title
        holder.itemView.tvRollingDate.text = curRollingFootage.dateAndTime

        fun onStart(holder: RollingViewHolder, uriString: String){
            if (Util.SDK_INT > 23) {
                initializePlayer(holder, uriString)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun initializePlayer(holder: RollingViewHolder, uriString: String){
        player = SimpleExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                holder.itemView.rolling_video_view.player = exoPlayer

                val mediaItem = MediaItem.fromUri(uriString)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = false
                exoPlayer.seekTo(0, 0L)
                exoPlayer.prepare()
            }
    }




}