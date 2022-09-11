package com.example.persistencia

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM "+User.table_name)
    fun getUsers() : Flow<List<User>>

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)
}