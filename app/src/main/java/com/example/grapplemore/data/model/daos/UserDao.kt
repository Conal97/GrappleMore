package com.example.grapplemore.data.model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.grapplemore.data.model.entities.UserEntity

// Dao to access the users table with possible SQL queries
@Dao
interface UserDao {

    // Write operations require coroutines, hence use of 'suspend'
    @Insert
    suspend fun  insert(register: UserEntity)

    @Query("select * from users_table where user_name like :userName")
    suspend fun getUsername(userName: String): UserEntity?

    @Query("select * from users_table order by userId desc")
    // output is LiveData of type list of user entity
    fun getAllUsers(): LiveData<List<UserEntity>>

}