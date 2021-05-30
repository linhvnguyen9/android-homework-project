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
            val iconRes = when (position) {
                0 -> R.drawable.ic_baseline_home_24
                1 -> R.drawable.ic_baseline_show_chart_24
                2 -> R.drawable.ic_baseline_account_circle_24
                else -> R.drawable.ic_baseline_home_24
            }
            tab.setIcon(iconRes)
        }.attach()
    }
}