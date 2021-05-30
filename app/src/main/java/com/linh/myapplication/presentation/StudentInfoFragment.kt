package com.linh.myapplication.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.linh.myapplication.LoginActivity
import com.linh.myapplication.databinding.FragmentStudentInfoBinding
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatActivity

class StudentInfoFragment : Fragment() {
    private lateinit var binding : FragmentStudentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        binding.textStudentInfoName.text = user?.displayName
        binding.textStudentInfoEmail.text = user?.email
        binding.textStudentInfoLogout.setOnClickListener {
            logout()
            requireActivity().finish()
        }
        binding.textStudentInfoSupportChat.setOnClickListener {
            val intent = Intent(requireContext(), StudentSupportChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}