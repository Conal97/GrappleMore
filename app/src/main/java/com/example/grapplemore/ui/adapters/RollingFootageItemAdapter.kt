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
import androidx.navigation.findNavController
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
    private val deleteListener: deleteItemCallBack,
    private val changeViewListener: changeViewCallBack,
    val context: Context
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

        // Get video thumbnail
        val videoParse = Uri.parse(curRollingFootage.videoUri)
        val size = Size(200,200)
        val thumbnail = context.contentResolver.loadThumbnail(videoParse,size,null)
        val ivThumb = holder.itemView.ivThumbnail
        ivThumb.setImageBitmap(thumbnail)

        // Set other parameters
        holder.itemView.tvRollingTitle.text = curRollingFootage.title
        holder.itemView.tvRollingDate.text = curRollingFootage.dateAndTime

        // Open editor on edit icon click
        holder.itemView.iveEditRolling.setOnClickListener {
            changeViewListener.changeViewCallBack(curRollingFootage)
            it.findNavController().navigate(R.id.
            action_rollingFootageFragment_to_rollingFootageSelectorFragment)
        }

        // Open viewer on play button click
        holder.itemView.ivPlayBtn.setOnClickListener {
            changeViewListener.changeViewCallBack(curRollingFootage)
            it.findNavController().navigate(R.id.
            action_rollingFootageFragment_to_rollingFootageViewerFragment)
        }

        // Delete item on delete icon click
        holder.itemView.ivDeleteRolling.setOnClickListener {
            deleteListener.deleteEntryCallBack(curRollingFootage)
        }
    }

    // callback interfaces here
    interface deleteItemCallBack{
        fun deleteEntryCallBack(rollingFootage: RollingFootage)
    }

    interface changeViewCallBack{
        fun changeViewCallBack(rollingFootage: RollingFootage)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}