package com.example.grapplemore.data.repositories

import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.EntryDao
import com.example.grapplemore.data.model.entities.Entry
import javax.inject.Inject

// Repository implements the functions defined in the dao interface, injection through dagger
class EntryRepository @Inject constructor(

    private val entryDao: EntryDao
) {

    suspend fun upsertEntry(entry: Entry){
        return entryDao.upsertEntry(entry)
    }

    suspend fun deleteEntry(entry: Entry){
        return entryDao.deleteEntry(entry)
    }

    fun getSingleEntry(fireBaseKey: String, id: Int): Entry {
        return entryDao.getSingleEntry(fireBaseKey, id)
    }

    fun getAllUserEntries(fireBaseKey: String): List<LiveData<Entry>> {
        return entryDao.getAllUserEntries(fireBaseKey)
    }

    fun getByTitle(fireBaseKey: String, title: String):List<LiveData<Entry>> {
        return entryDao.getByTitle(fireBaseKey, title)
    }

    fun getByCategory(fireBaseKey: String, category: String): List<LiveData<Entry>>{
        return entryDao.getByCategory(fireBaseKey, category)
    }

    fun getByCategoryAndTitle(fireBaseKey: String, category: String, title: String): List<LiveData<Entry>>{
        return entryDao.getByCategoryAndTitle(fireBaseKey, category, title)
    }
}