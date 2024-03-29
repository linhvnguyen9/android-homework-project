package com.linh.myapplication.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.linh.myapplication.R
import com.linh.myapplication.databinding.FragmentHomeBinding
import com.linh.myapplication.domain.Announcement
import kotlinx.android.synthetic.main.activity_main.*
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

        binding.recyclerviewAnnoucements.adapter = AnnouncementsListAdapter(object : AnnouncementClickListener {
            override fun onClick(announcement: Announcement) {
                requireActivity().supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                    replace(R.id.container, AnnouncementDetailFragment.newInstance(announcement))
                    addToBackStack(HomeFragment::class.java.simpleName)
                }
            }
        })
        binding.recyclerviewAnnoucements.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewAnnoucements.isNestedScrollingEnabled = false
        binding.textWelcome.text = "Chào mừng ${Firebase.auth.currentUser.displayName}"

        viewModel.announcements.observe(viewLifecycleOwner) {
            (binding.recyclerviewAnnoucements.adapter as AnnouncementsListAdapter).submitList(it)
        }
    }
}