package com.example.stupa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stupa.room.UserDao
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao: UserDao) : ViewModel() {
    private val _navigateToNextScreen = MutableLiveData<Boolean>()
    val navigateToNextScreen: LiveData<Boolean> = _navigateToNextScreen

    fun validateCredentials(email: String, password: String) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(email)
            _navigateToNextScreen.value = user != null && user.password == password
        }
    }
}