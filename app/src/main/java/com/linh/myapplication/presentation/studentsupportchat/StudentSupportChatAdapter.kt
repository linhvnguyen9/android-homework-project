package com.linh.myapplication.presentation.studentsupportchat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.linh.myapplication.databinding.ItemChatBinding
import com.linh.myapplication.domain.ChatMessage

class StudentSupportChatAdapter(private val options: FirebaseRecyclerOptions<ChatMessage>, private val currentUserName: String?) :
    FirebaseRecyclerAdapter<ChatMessage, StudentSupportChatAdapter.ChatViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int, model: ChatMessage) {
        holder.bind(model)
    }

    class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.messageTextView.text = message.text
            binding.messengerTextView.text = message.name
        }

        companion object {
            fun from(parent: ViewGroup): ChatViewHolder {
                val binding =
                    ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return ChatViewHolder(binding)
            }
        }
    }
}