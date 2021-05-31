package com.linh.myapplication.presentation.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.linh.myapplication.R
import com.linh.myapplication.domain.Schedule
import kotlinx.android.synthetic.main.fragment_grade.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleFragment : Fragment() {
    private val viewModel :ScheduleViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScheduleListAdapter(requireContext())

        recycler_view_grade_list.adapter = adapter
        recycler_view_grade_list.layoutManager = LinearLayoutManager(requireContext())

        viewModel.schedule.observe(viewLifecycleOwner) {
            if (it != null) {
                (recycler_view_grade_list.adapter as ScheduleListAdapter).submitList(it)
            }
        }
    }
}