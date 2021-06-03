package com.linh.myapplication.presentation.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.linh.myapplication.R
import com.linh.myapplication.databinding.FragmentGradeBinding
import com.linh.myapplication.domain.Schedule
import kotlinx.android.synthetic.main.fragment_grade.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentGradeBinding

    private val viewModel :ScheduleViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGradeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScheduleListAdapter(requireContext())

        binding.recyclerViewGradeList.adapter = adapter
        binding.recyclerViewGradeList.layoutManager = LinearLayoutManager(requireContext())
        binding.searchSchedule.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return onQueryTextChange(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSchedule(newText ?: "", null)

                return true
            }
        })
        binding.spinnerOrder.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.sort, android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOrder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        viewModel.getSchedule(null, "time")
                    }
                    1 -> {
                        viewModel.getSchedule(null, "name")
                    }
                    2 -> {
                        viewModel.getSchedule(null, "teacher")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.getSchedule()
            }
        }

        viewModel.schedule.observe(viewLifecycleOwner) {
            if (it != null) {
                (binding.recyclerViewGradeList.adapter as ScheduleListAdapter).submitList(it)
            }
        }
    }
}