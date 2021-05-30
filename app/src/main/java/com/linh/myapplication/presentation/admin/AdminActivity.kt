package com.linh.myapplication.presentation.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.linh.myapplication.R
import com.linh.myapplication.databinding.ActivityAdminBinding
import com.linh.myapplication.domain.Chat
import com.linh.myapplication.domain.ChatMessage
import com.linh.myapplication.presentation.studentsupportchat.ScrollToBottomObserver
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatActivity
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatAdapter
import timber.log.Timber

class AdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        db = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val chatRef = db.reference.child(CHATS_CHILD)
        val options = FirebaseRecyclerOptions.Builder<Chat>()
            .setQuery(chatRef, Chat::class.java)
            .build()

        Timber.d(options.snapshots.toString())

        val layoutManager = LinearLayoutManager(this)
        val adapter = AdminChatAdapter(options)

        binding.recyclerAdminChatList.layoutManager = layoutManager
        binding.recyclerAdminChatList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        (binding.recyclerAdminChatList.adapter as? AdminChatAdapter)?.startListening()
    }

    override fun onPause() {
        super.onPause()

        (binding.recyclerAdminChatList.adapter as? AdminChatAdapter)?.stopListening()
    }

    companion object {
        private const val CHATS_CHILD = "chats"
    }
}