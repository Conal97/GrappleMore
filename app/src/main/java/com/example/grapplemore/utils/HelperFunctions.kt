package com.example.grapplemore.utils

import android.content.Context
import com.example.grapplemore.data.model.entities.ArchiveEntry

object HelperFunctions {

    fun colourMapper(archiveEntry: ArchiveEntry, context: Context): Int {

        val categoryMap = mapOf(
            "Class Note" to "@drawable/rounded_orange_border",
            "Submission" to "@drawable/rounded_submission_background",
            "Position" to "@drawable/rounded_position_green",
            "Sweep/Pass" to "@drawable/rounded_sweep_pass_purple",
            "Takedown/Throw" to "@drawable/rounded_takedownthrow_yello",
            "Escape" to "@drawable/rounded_escape_blue"
        )

        val categoryColour = categoryMap.get(archiveEntry.category)

        return context.resources.getIdentifier(categoryColour, "drawable", context.packageName)
    }

    fun monthMap(monthInt: Int): String? {

        val monthMap = mapOf(
            1 to "Jan",
            2 to "Feb",
            3 to "Mar",
            4 to "Apr",
            5 to "May",
            6 to "Jun",
            7 to "Jul",
            8 to "Aug",
            9 to "Sept",
            10 to "Oct",
            11 to "Nov",
            12 to "Dec"
        )

        return monthMap[monthInt]
    }
}