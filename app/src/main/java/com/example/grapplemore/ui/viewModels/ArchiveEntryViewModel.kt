package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.data.repositories.ArchiveEntryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveEntryViewModel @Inject constructor(
    private val archiveEntryRepository: ArchiveEntryRepository
): ViewModel(){

    // Live data to access ArchiveEntry outside viewModel scope
    private var _currentArchiveEntry = MutableLiveData<ArchiveEntry?>()
    val currentArchiveEntry: MutableLiveData<ArchiveEntry?>
        get() = _currentArchiveEntry

    fun getCurrentEntry(entry: ArchiveEntry){
        currentArchiveEntry.value = entry
    }

    // Get selected archive entry
    fun getSingleEntry(fireBaseKey: String, id: Int) {
        viewModelScope.launch {
            val archiveEntry = archiveEntryRepository.getSingleEntry(fireBaseKey, id)
            if (archiveEntry != null){
                _currentArchiveEntry.value = archiveEntry
            }
        }
    }

    // Get all user archive entries
    fun getAllUserEntries(fireBaseKey: String):LiveData<List<ArchiveEntry>>{
        return archiveEntryRepository.getAllUserEntries(fireBaseKey)
    }

    // Get archive entries by title
    fun getByTitle(fireBaseKey: String, title: String): LiveData<List<ArchiveEntry>> {
        return archiveEntryRepository.getByTitle(fireBaseKey, title)
    }

    // Get archive entries by category
    fun getByCategory(fireBaseKey: String, category: String): LiveData<List<ArchiveEntry>>{
        return archiveEntryRepository.getByCategory(fireBaseKey, category)
    }

    // Insert or update archive entry
    fun upsertEntry(archiveEntry: ArchiveEntry){ // possibly define dispatcher?
        viewModelScope.launch {
            archiveEntryRepository.upsertEntry(archiveEntry)
        }
    }
    // Delete archive entry
   fun deleteArchiveEntry(archiveEntry: ArchiveEntry){ // possibly define dispatcher?
       viewModelScope.launch {
           archiveEntryRepository.deleteEntry(archiveEntry)
       }
   }
}