package com.linh.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.linh.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}