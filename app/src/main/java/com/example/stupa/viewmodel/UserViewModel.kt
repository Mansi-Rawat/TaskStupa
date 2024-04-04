package com.example.stupa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.stupa.room.User
import com.example.stupa.room.UserDao

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    val userList: LiveData<List<User>> = userDao.getAllUsers() }

