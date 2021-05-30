package com.linh.myapplication.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linh.myapplication.R
import com.linh.myapplication.databinding.FragmentAnnouncementDetailBinding
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.presentation.CalendarUtl

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AnnouncementDetailFragment : Fragment() {
    private lateinit var binding : FragmentAnnouncementDetailBinding

    private var param1: Announcement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAnnouncementDetailBinding.inflate(inflater, container, false)
        binding.annoucement = param1
        binding.toolbar.title = param1?.title
        binding.date.text = "Cập nhật ngày ${CalendarUtl.toFormattedString(param1?.timestamp ?: 0)}"
        binding.fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain";
            intent.putExtra(Intent.EXTRA_TEXT, param1?.content)
            intent.putExtra(Intent.EXTRA_SUBJECT, param1?.title)
            startActivity(Intent.createChooser(intent, "Share using..."))
        }
        return binding.root
    }

    companion object {
        @JvmStatic fun newInstance(param1: Announcement) =
                AnnouncementDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_PARAM1, param1)
                    }
                }
    }
}