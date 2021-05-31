package com.linh.myapplication.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linh.myapplication.presentation.home.HomeFragment
import com.linh.myapplication.presentation.result.ScheduleFragment

class ViewPagerAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> ScheduleFragment()
            2 -> StudentInfoFragment()
            else -> HomeFragment()
        }
    }
}