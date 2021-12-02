package com.example.grapplemore.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.TrainingEvent
import kotlinx.android.synthetic.main.training_event_item.view.*
import java.text.SimpleDateFormat
import com.example.grapplemore.utils.HelperFunctions.monthMap
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class TrainingEventAdapterPrevious(

    var items: List<TrainingEvent>,
    private val deleteListener: TrainingEventAdapterPrevious.deletePreviousTrainingCallBack,
    private val editListener: TrainingEventAdapterPrevious.editPreviousTrainingCallBack,
    val context: Context
): RecyclerView.Adapter<TrainingEventAdapterPrevious.TrainingViewHolder>() {

    inner class TrainingViewHolder(trainingEventItem: View): RecyclerView.ViewHolder(trainingEventItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        return TrainingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.training_event_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {

        val curTrainingEvent = items[position]

        // Set view
        val startTime = curTrainingEvent.startTime.slice((11..15))
        val endTime = curTrainingEvent.endTime.slice((11..15))

        val day = curTrainingEvent.startTime.slice((0..1))
        val month = curTrainingEvent.startTime.slice((3..4))
        val timeRange = "$startTime - $endTime"

        holder.itemView.tvDayOfWeek.text = curTrainingEvent.dayOfWeek
        holder.itemView.tvDay.text = day
        holder.itemView.tvMonth.text = monthMap(month.toInt())
        holder.itemView.tvClassTitle.text = curTrainingEvent.title
        holder.itemView.tvTimeSlot.text = timeRange
        holder.itemView.tvDay.setTextColor(Color.parseColor("#44db86"))
        holder.itemView.tvDay.setTextColor(Color.parseColor("#de3e4e"))
        val drawId = context.resources.getIdentifier("@drawable/rounded_grey_background", "drawable", context.packageName)
        holder.itemView.eventItemConstraint.background = context.resources.getDrawable(drawId)

        // Implement on click listeners here
        // Edit item
        holder.itemView.editEventButton.setOnClickListener {
            editListener.editPreviousTrainingCallBack(curTrainingEvent)
            it.findNavController().navigate(R.id
                .action_trainingScheduleFragment_to_trainingEventAddEditFragment)
        }

        // Delete item
        holder.itemView.deleteEventButton.setOnClickListener {
            deleteListener.deletePreviousTrainingCallBack(curTrainingEvent)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface deletePreviousTrainingCallBack{
        fun deletePreviousTrainingCallBack(trainingEvent: TrainingEvent)
    }

    interface editPreviousTrainingCallBack{
        fun editPreviousTrainingCallBack(trainingEvent: TrainingEvent)
    }


}