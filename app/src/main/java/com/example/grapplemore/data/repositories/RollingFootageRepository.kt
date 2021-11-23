package com.example.grapplemore.data.repositories

import androidx.lifecycle.LiveData
import com.example.grapplemore.data.model.daos.RollingFootageDao
import com.example.grapplemore.data.model.entities.RollingFootage
import javax.inject.Inject

class RollingFootageRepository @Inject constructor(
    private val rollingFootageDao: RollingFootageDao
) {

    suspend fun insertRollingFootage(rollingFootage: RollingFootage){
        return rollingFootageDao.insertRollingFootage(rollingFootage)
    }

    suspend fun deleteRollingFootage(rollingFootage: RollingFootage){
        return rollingFootageDao.deleteRollingFootage(rollingFootage)
    }

    fun getRollingFootage(fireBaseKey: String): LiveData<List<RollingFootage>>{
        return rollingFootageDao.getAll(fireBaseKey)
    }
}