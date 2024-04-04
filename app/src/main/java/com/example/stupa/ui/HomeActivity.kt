package com.example.stupa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.stupa.R
import com.example.stupa.adapter.UserPagerAdapter
import com.example.stupa.factory.UserViewModelFactory
import com.example.stupa.room.AppDatabase
import com.example.stupa.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userViewModelFactory: UserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userDao = AppDatabase.getInstance(application).userDao()
        userViewModelFactory = UserViewModelFactory(userDao)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userDao)).get(UserViewModel::class.java)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val tabNames = arrayListOf("Stupa 1", "Stupa 2", "Stupa 3")
        val pagerAdapter = UserPagerAdapter(this, tabNames)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}
