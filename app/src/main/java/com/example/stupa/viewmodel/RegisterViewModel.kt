package com.example.stupa.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stupa.room.User
import com.example.stupa.room.UserDao
import kotlinx.coroutines.launch

class RegisterViewModel(private val userDao: UserDao) : ViewModel() {
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    fun validateForm(name: String, phoneNumber: String, email: String, password: String) {
        val isValid = when {
            name.isBlank() -> false
            !Patterns.PHONE.matcher(phoneNumber).matches() -> false
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            password.length < 6 -> false
            else -> true
        }
        _isFormValid.value = isValid
    }


    fun saveUserToDatabase(
        name: String,
        phoneNumber: String,
        email: String,
        password: String,
        city: String
    ): Boolean {
        return try {
            val user = User(name = name, phoneNumber = phoneNumber, email = email, password = password, city = city)
            viewModelScope.launch {
                userDao.insertUser(user)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

