package com.technologies.theone.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.technologies.theone.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("select * from users")
    fun getUsers(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)
}