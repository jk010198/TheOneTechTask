package com.technologies.theone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technologies.theone.model.User
import com.technologies.theone.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel (private val contactRepo: UserRepo) : ViewModel() {

    fun insertUser(contactDetails: User) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.insertUser(contactDetails)
        }
    }

    fun deleteuser(contactDetails: User) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepo.deleteuser(contactDetails)
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return contactRepo.getAllUsers()
    }

}