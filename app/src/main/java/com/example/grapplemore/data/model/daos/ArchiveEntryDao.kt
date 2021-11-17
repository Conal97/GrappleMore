package com.example.grapplemore.data.model.daos
//
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.grapplemore.data.model.entities.ArchiveEntry
//
//@Dao
//interface ArchiveEntryDao {
//
//    // Add or update entry
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun upsertArchiveEntry(archiveEntry: ArchiveEntry)
//
//    // Delete entry
//    @Delete
//    suspend fun deleteArchiveEntry(archiveEntry: ArchiveEntry)
//
//    // Get singular entry belonging to user (i.e. when clicked)
//    @Query("select * from ArchiveEntry where fireBaseKey = :fireBaseKey and id = :id")
//    fun getSingleArchiveEntry(fireBaseKey: String, id: Int): ArchiveEntry
//
//    // Get all entries belonging to user
//    @Query("select * from ArchiveEntry where firebaseKey = :fireBaseKey")
//    fun getAllUserArchiveEntries(fireBaseKey: String): LiveData<List<ArchiveEntry>>
//
//    // Get entries by title
//    @Query("select * from ArchiveEntry where fireBaseKey = :fireBaseKey and title like :title")
//    fun getByTitle(fireBaseKey: String, title: String): LiveData<List<ArchiveEntry>>
//
//    // Get entries by category
//    @Query("select * from ArchiveEntry where fireBaseKey = :fireBaseKey and category = :category")
//    fun getByCategory(fireBaseKey: String, category: String): LiveData<List<ArchiveEntry>>
//
//    // Get entries by category and title
//    @Query("select * from ArchiveEntry where fireBaseKey = :fireBaseKey and category=:category and title like :title")
//    fun getByCategoryAndTitle(
//        fireBaseKey: String,
//        category: String,
//        title: String
//    ): LiveData<List<ArchiveEntry>>
//}