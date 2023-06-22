package com.technologies.theone.repository

import androidx.lifecycle.LiveData
import com.technologies.theone.dao.UserDao
import com.technologies.theone.model.User

class UserRepo ( val dao: UserDao) {

    suspend fun insertUser(user: User){
        dao.insertUser(user)
    }

    suspend fun deleteuser(user: User){
        dao.deleteUser(user)
    }

    fun getAllUsers() : LiveData<List<User>> {
        return dao.getUsers()
    }
}