package com.linh.myapplication.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.linh.myapplication.presentation.result.GradeFragment
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> GradeFragment()
            2 -> StudentSupportChatFragment()
            else -> HomeFragment()
        }
    }
}