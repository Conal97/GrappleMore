package com.example.grapplemore.data.repositories

import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.ArchiveEntryDao
import com.example.grapplemore.data.model.entities.ArchiveEntry
import javax.inject.Inject

// Repository implements the functions defined in the dao interface, injection through dagger
class ArchiveEntryRepository @Inject constructor(
    private val archiveEntryDao: ArchiveEntryDao
) {

    suspend fun upsertEntry(archiveEntry: ArchiveEntry){
        return archiveEntryDao.upsertArchiveEntry(archiveEntry)
    }

    suspend fun deleteEntry(archiveEntry: ArchiveEntry){
        return archiveEntryDao.deleteArchiveEntry(archiveEntry)
    }

    fun getAllUserEntries(fireBaseKey: String): LiveData<List<ArchiveEntry>> {
        return archiveEntryDao.getAllUserArchiveEntries(fireBaseKey)
    }

    fun getByTitle(fireBaseKey: String, title: String):LiveData<List<ArchiveEntry>> {
        return archiveEntryDao.getByTitle(fireBaseKey, title)
    }

    fun getByCategory(fireBaseKey: String, category: String): LiveData<List<ArchiveEntry>>{
        return archiveEntryDao.getByCategory(fireBaseKey, category)
    }

    fun getByCategoryAndTitle(fireBaseKey: String, category: String, title: String): LiveData<List<ArchiveEntry>>{
        return archiveEntryDao.getByCategoryAndTitle(fireBaseKey, category, title)
    }
}