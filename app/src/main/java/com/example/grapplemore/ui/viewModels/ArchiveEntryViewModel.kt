package com.example.grapplemore.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.example.grapplemore.data.repositories.ArchiveEntryRepository
import com.google.firebase.auth.FirebaseAuth
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

    // Get all user archive entries
    fun getAllUserEntries(fireBaseKey: String):LiveData<List<ArchiveEntry>>{
        return archiveEntryRepository.getAllUserEntries(fireBaseKey)
    }

    // Get archive entries by title
    fun getByTitle(fireBaseKey: String, title: String): LiveData<List<ArchiveEntry>> {
        return archiveEntryRepository.getByTitle(fireBaseKey, title)
    }

    // Get by title and category
    fun getByTitleAndCategory(fireBaseKey: String, category:String, title: String): LiveData<List<ArchiveEntry>> {
        return archiveEntryRepository.getByCategoryAndTitle(fireBaseKey, category, title)
    }

    // Get archive entries by category
    fun getByCategory(fireBaseKey: String, category: String): LiveData<List<ArchiveEntry>>{
        return archiveEntryRepository.getByCategory(fireBaseKey, category)
    }

    // Insert or update archive entry
    fun upsertEntry(archiveEntry: ArchiveEntry){
        viewModelScope.launch {
            archiveEntryRepository.upsertEntry(archiveEntry)
        }
    }

    // Delete archive entry
   fun deleteArchiveEntry(archiveEntry: ArchiveEntry){
       viewModelScope.launch {
           archiveEntryRepository.deleteEntry(archiveEntry)
       }
   }
}