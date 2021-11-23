package com.example.grapplemore.data.model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import com.example.grapplemore.data.model.entities.RollingFootage

@Dao
interface RollingFootageDao {

    // Add rolling footage
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRollingFootage(rollingFootage: RollingFootage)

    // Delete rolling footage
    @Delete
    suspend fun deleteRollingFootage(rollingFootage: RollingFootage)

    // Get all footage from user
    @Query("select * from RollingFootage where fireBaseKey =:fireBaseKey")
    fun getAll(fireBaseKey: String): LiveData<List<RollingFootage>>

}