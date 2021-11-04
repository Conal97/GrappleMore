package com.example.grapplemore.data.repositories

import com.example.grapplemore.data.model.daos.UserDao
import com.example.grapplemore.data.model.entities.UserEntity
import javax.inject.Inject

// Repository implements the functions defined in the dao interface, injection through dagger
class UserRepository @Inject constructor (
    private val userDao: UserDao
    ) {

    fun getAllUsers() = userDao.getAllUsers()

    suspend fun insert(user: UserEntity) {
        return userDao.insert(user)
    }

    suspend fun getUser(userName: String):UserEntity? {
        return userDao.getUser(userName)
    }
}