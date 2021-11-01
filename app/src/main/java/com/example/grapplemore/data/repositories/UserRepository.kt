package com.example.grapplemore.data.repositories

import com.example.grapplemore.data.model.daos.UserDao
import com.example.grapplemore.data.model.entities.UserEntity

// Repository implements the functions defined in the dao interface
class UserRepository(private val dao: UserDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user: UserEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String):UserEntity? {
        return dao.getUsername(userName)
    }
}