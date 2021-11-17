package com.example.grapplemore.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.ui.viewModels.ArchiveEntryViewModel
import kotlinx.android.synthetic.main.archive_item.view.*

class ArchiveItemAdapter(
    var items: List<ArchiveEntry>,
    private val viewModel: ArchiveEntryViewModel
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

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val curArchiveEntry = items[position]

        holder.itemView.tvTitle.text = curArchiveEntry.title
        holder.itemView.tvCategory.text = curArchiveEntry.category
        holder.itemView.tvDate.text = curArchiveEntry.timestamp

        // Put click listener here -> nav to appropriate archive entry
        // holder.itemView.setOnClickListener()

    }

    override fun getItemCount(): Int {
        return items.size
    }
}