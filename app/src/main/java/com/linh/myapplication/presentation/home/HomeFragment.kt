package com.linh.myapplication.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.linh.myapplication.R
import com.linh.myapplication.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewAnnoucements.adapter = AnnouncementsListAdapter()
        binding.recyclerviewAnnoucements.layoutManager = LinearLayoutManager(requireContext())

        viewModel.announcements.observe(viewLifecycleOwner) {
            (binding.recyclerviewAnnoucements.adapter as AnnouncementsListAdapter).submitList(it)
        }
    }
}