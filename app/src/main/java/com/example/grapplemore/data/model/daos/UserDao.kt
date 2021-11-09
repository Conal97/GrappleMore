package com.example.grapplemore.data.model.daos
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.grapplemore.data.model.entities.UserEntity
//
//
//// Dao to access the users table with possible SQL queries
//@Dao
//interface UserDao {
//
//    // Insert new userProfile
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    suspend fun  addProfile(profile: UserEntity)
//
//    // Update user profile
//    @Update
//    suspend fun updateProfile(profile: UserEntity)
//
//    // Get a single user entity by id
//    @Query("select * from users_table where userId = userId")
//    suspend fun getUser(userName: String): UserEntity?
//
//    // Get all current userNames
//    @Query("select userName from users_table order by userId desc")
//    // output is LiveData of type list of user entity
//    fun getAllUsers(): LiveData<List<UserEntity>>
//
//}