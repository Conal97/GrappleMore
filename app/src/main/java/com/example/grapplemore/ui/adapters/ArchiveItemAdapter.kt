package com.example.grapplemore.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.ui.viewModels.ArchiveEntryViewModel
import kotlinx.android.synthetic.main.archive_item.view.*
import timber.log.Timber

class ArchiveItemAdapter(
    var items: List<ArchiveEntry>,
    private val idListener: callBackInterface,
    private val archiveEntryViewModel: FragmentActivity,
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

        val categoryMap = mapOf(
            "Class Note" to "@drawable/rounded_orange_border",
            "Submission" to "@drawable/rounded_submission_background",
            "Position" to "@drawable/rounded_position_green",
            "Sweep/Pass" to "@drawable/rounded_sweep_pass_purple",
            "Takedown/Throw" to "@drawable/rounded_takedownthrow_yello",
            "Escape" to "@drawable/rounded_escape_blue"
        )

        val categoryColour = categoryMap.get(curArchiveEntry.category)
        val categoryDrawable = context.resources.getIdentifier(categoryColour, "drawable", context.packageName)

        holder.itemView.tvTitle.text = curArchiveEntry.title
        holder.itemView.tvCategory.text = curArchiveEntry.category
        holder.itemView.tvDate.text = curArchiveEntry.timestamp
        holder.itemView.itemConstraint.background = context.resources.getDrawable(categoryDrawable)

        holder.itemView.setOnClickListener{
            curArchiveEntry.id?.let { it1 -> idListener.passResultsCallback(it1) }
            it.findNavController().navigate(R.id.action_techniquesArchiveFragment_to_archiveEntryFragment)
        }
    }

    interface callBackInterface{
        fun passResultsCallback(archiveEntryID: Int)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}