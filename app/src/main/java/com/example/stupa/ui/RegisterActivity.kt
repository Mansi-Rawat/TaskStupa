package com.example.stupa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.stupa.R
import com.example.stupa.databinding.ActivityRegisterBinding
import com.example.stupa.factory.RegisterViewModelFactory
import com.example.stupa.room.AppDatabase
import com.example.stupa.room.UserDao
import com.example.stupa.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDao = AppDatabase.getInstance(application).userDao()

        val viewModelFactory = RegisterViewModelFactory(userDao)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val cityOptions = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = adapter

        binding.buttonRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val city = binding.spinnerCity.selectedItem.toString()

            if (name.isBlank() || phoneNumber.isBlank() || email.isBlank() || password.isBlank() || city.isBlank()) {
                Toast.makeText(this, "Please fill in all fields and password must be at least 3 characters long,and email must be in a valid format", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.validateForm(name, phoneNumber, email, password)
            }
        }

        viewModel.isFormValid.observe(this) { isValid ->
            if (isValid) {
                validateAndRegister()
            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateAndRegister() {
        val name = binding.editTextName.text.toString()
        val phoneNumber = binding.editTextPhoneNumber.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val city = binding.spinnerCity.selectedItem.toString()

        val isSaved = viewModel.saveUserToDatabase(name, phoneNumber, email, password, city)
        if (isSaved) {
            Toast.makeText(this, "User is registered", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
        }
    }
}




