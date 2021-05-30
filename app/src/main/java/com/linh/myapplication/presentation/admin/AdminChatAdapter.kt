package com.linh.myapplication.presentation.admin

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.linh.myapplication.databinding.ItemAdminChatBinding
import com.linh.myapplication.domain.Chat
import com.linh.myapplication.presentation.studentsupportchat.StudentSupportChatActivity

class AdminChatAdapter(options: FirebaseRecyclerOptions<Chat>) :
    FirebaseRecyclerAdapter<Chat, RecyclerView.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AdminChatViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        model: Chat
    ) {
        (holder as AdminChatViewHolder).bind(model)
    }

    class AdminChatViewHolder(private val binding: ItemAdminChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.textAdminChatName.text = chat.studentName
            binding.textAdminChatLastMessage.text = chat.lastMessage
            Glide.with(binding.root).load(chat.studentAvatarUrl).circleCrop()
                .into(binding.imageAdminChatAvatar)
            binding.frameAdminChat.setOnClickListener {
                val context = binding.root.context

                val intent = Intent(context, StudentSupportChatActivity::class.java)
                intent.putExtra(StudentSupportChatActivity.EXTRA_STUDENT_UID, chat.studentUid)
                context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AdminChatViewHolder {
                val binding =
                    ItemAdminChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return AdminChatViewHolder(binding)
            }
        }
    }
}