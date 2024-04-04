package com.example.stupa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stupa.fragment.TabFragment

class UserPagerAdapter(fragmentActivity: FragmentActivity, private val tabNames: List<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = tabNames.size

    override fun createFragment(position: Int): Fragment {
        return TabFragment()
    }
}