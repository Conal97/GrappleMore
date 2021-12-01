package com.example.grapplemore.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grapplemore.R
import com.example.grapplemore.data.model.entities.TrainingEvent
import kotlinx.android.synthetic.main.training_event_item.view.*
import java.text.SimpleDateFormat
import com.example.grapplemore.utils.HelperFunctions.monthMap
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class TrainingEventAdapterUpcoming(

    var items: List<TrainingEvent>,
    private val deleteListener: deleteTrainingCallBack,
    private val editListener: editTrainingCallBack,
    val context: Context
): RecyclerView.Adapter<TrainingEventAdapterUpcoming.TrainingViewHolder>() {

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

        // Implement on click listeners here

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface deleteTrainingCallBack{
        fun deleteTrainingCallBack(trainingEvent: TrainingEvent)
    }

    interface editTrainingCallBack{
        fun editTrainingCallBack(trainingEvent: TrainingEvent)
    }


}