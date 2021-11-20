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
}