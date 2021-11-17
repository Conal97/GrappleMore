package com.example.grapplemore.data.model.daos


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.grapplemore.data.model.entities.Entry

@Dao
interface EntryDao {

    // Add or update entry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEntry(entry: Entry)

    // Delete entry
    @Delete
    suspend fun deleteEntry(entry: Entry)

    // Get singular entry belonging to user (i.e. when clicked)
    @Query("select * from entry where fireBaseKey = :fireBaseKey and id = :id")
    fun getSingleEntry(fireBaseKey: String, id: Int): Entry

    // Get all entries belonging to user
    @Query("select * from entry where firebaseKey = :fireBaseKey")
    fun getAllUserEntries(fireBaseKey: String): List<LiveData<Entry>>

    // Get entries by title
    @Query("select * from entry where fireBaseKey = :fireBaseKey and title like :title")
    fun getByTitle(fireBaseKey: String, title: String): List<LiveData<Entry>>

    // Get entries by category
    @Query("select * from entry where fireBaseKey = :fireBaseKey and category = :category")
    fun getByCategory(fireBaseKey: String, category: String): List<LiveData<Entry>>

    // Get entries by category and title
    @Query("select * from entry where fireBaseKey = :fireBaseKey and category=:category and title like :title")
    fun getByCategoryAndTitle(fireBaseKey: String, category: String, title: String): List<LiveData<Entry>>
}