package com.linh.myapplication.presentation.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.linh.myapplication.R
import com.linh.myapplication.domain.Result
import kotlinx.android.synthetic.main.fragment_grade.*

class GradeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GradeListAdapter(requireContext())
        adapter.submitList(listOf(
            Result("Cấu trúc dữ liệu và giải thuật", 10.0f, "A+"),
            Result("Lập trình hướng đối tượng", 10.0f, "A+"),
            Result("Đại số", 10.0f, "A+"),
        ))

        recycler_view_grade_list.adapter = adapter
        recycler_view_grade_list.layoutManager = LinearLayoutManager(requireContext())
    }
}