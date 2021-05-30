package com.linh.myapplication.presentation.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.linh.myapplication.R
import com.linh.myapplication.databinding.ActivityAdminBinding
import com.linh.myapplication.databinding.ActivityAdminComposeAnnouncementBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminComposeAnnouncementActivity : AppCompatActivity() {
    private val viewModel : AdminComposeAnnouncementViewModel by viewModel()
    private lateinit var binding : ActivityAdminComposeAnnouncementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminComposeAnnouncementBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.buttonSend.setOnClickListener {
            viewModel.sendAnnouncement()
        }
    }
}