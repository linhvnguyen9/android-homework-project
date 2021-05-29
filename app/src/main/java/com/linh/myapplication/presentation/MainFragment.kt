package com.linh.myapplication.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.linh.myapplication.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(tab, viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Trang chủ"
                1 -> "Điểm thi"
                2 -> "Sinh viên"
                else -> ""
            }
        }.attach()

        navigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.home -> {
                    viewpager.currentItem = 0
                    true
                }
                R.id.result -> {
                    viewpager.currentItem = 1
                    true
                }
                R.id.studentInfo -> {
                    viewpager.currentItem = 2
                    true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}