package com.linh.myapplication.presentation.studentsupportchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.linh.myapplication.databinding.FragmentStudentSupportChatBinding
import com.linh.myapplication.domain.ChatMessage
import timber.log.Timber

class StudentSupportChatFragment : Fragment() {
    private lateinit var binding: FragmentStudentSupportChatBinding

    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentSupportChatBinding.inflate(inflater, container, false)

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance()
        val messagesRef = db.reference.child(MESSAGES_CHILD)

        val options = FirebaseRecyclerOptions.Builder<ChatMessage>()
            .setQuery(messagesRef, ChatMessage::class.java)
            .build()

        Timber.d(options.snapshots.toString())

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true

        val adapter = StudentSupportChatAdapter(options, ANONYMOUS)

        binding.recyclerStudentSupportChat.layoutManager = layoutManager
        binding.recyclerStudentSupportChat.adapter = adapter

        adapter.
            registerAdapterDataObserver(
                ScrollToBottomObserver(
                    binding.recyclerStudentSupportChat, adapter,
                    layoutManager
                )
            )
    }

    override fun onResume() {
        super.onResume()

        (binding.recyclerStudentSupportChat.adapter as? StudentSupportChatAdapter)?.startListening()
    }

    override fun onPause() {
        super.onPause()

        (binding.recyclerStudentSupportChat.adapter as? StudentSupportChatAdapter)?.stopListening()
    }

    companion object {
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}