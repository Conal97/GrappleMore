package com.example.grapplemore.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.utils.HelperFunctions.colourMapper
import kotlinx.android.synthetic.main.archive_item.view.*

class ArchiveItemAdapter(
    var items: List<ArchiveEntry>,
    private val idListener: callBackInterface,
    private val deleteListener: deleteCallBack,
    val context: Context
): RecyclerView.Adapter<ArchiveItemAdapter.ArchiveViewHolder>() {

    inner class ArchiveViewHolder(archiveItem: View): RecyclerView.ViewHolder(archiveItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        return ArchiveViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.archive_item,
                parent,
                false
            )
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {

        val curArchiveEntry = items[position]
        val drawableId = colourMapper(curArchiveEntry, context)

        // Set the view
        holder.itemView.tvTitle.text = curArchiveEntry.title
        holder.itemView.tvCategory.text = curArchiveEntry.category
        holder.itemView.tvDate.text = curArchiveEntry.timestamp
        holder.itemView.itemConstraint.background = context.resources.getDrawable(drawableId)

        // Open entry fragment on click
        holder.itemView.setOnClickListener{
            idListener.passResultsCallback(curArchiveEntry)
            it.findNavController().navigate(R.id.action_techniquesArchiveFragment_to_archiveEntryFragment)
        }

        holder.itemView.deleteButton.setOnClickListener {
            deleteListener.deleteEntryCallBack(curArchiveEntry)
        }
    }
    interface deleteCallBack{
        fun deleteEntryCallBack(archiveEntry: ArchiveEntry)
    }

    interface callBackInterface{
        fun passResultsCallback(archiveEntry: ArchiveEntry)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}