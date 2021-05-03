package com.linh.myapplication.presentation.studentsupportchat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.linh.myapplication.databinding.ItemChatBinding
import com.linh.myapplication.databinding.ItemImageChatBinding
import com.linh.myapplication.domain.ChatMessage

class StudentSupportChatAdapter(private val options: FirebaseRecyclerOptions<ChatMessage>, private val currentUserName: String?) :
    FirebaseRecyclerAdapter<ChatMessage, RecyclerView.ViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_IMAGE -> {
                ChatImageViewHolder.from(parent)
            }
            VIEW_TYPE_TEXT -> {
                ChatViewHolder.from(parent)
            }
            else -> {
                ChatViewHolder.from(parent)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        model: ChatMessage
    ) {
        if (options.snapshots[position].text != null) {
            (holder as ChatViewHolder).bind(model)
        } else {
            (holder as ChatImageViewHolder).bind(model)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (options.snapshots[position].text != null) VIEW_TYPE_TEXT else VIEW_TYPE_IMAGE
    }

    class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.messageTextView.text = message.text
            binding.messengerTextView.text = message.name
            Glide.with(binding.root).load(message.photoUrl).circleCrop().into(binding.messengerImageView)
        }

        companion object {
            fun from(parent: ViewGroup): ChatViewHolder {
                val binding =
                    ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return ChatViewHolder(binding)
            }
        }
    }

    class ChatImageViewHolder(private val binding: ItemImageChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.messengerTextView.text = message.name
            Glide.with(binding.root).load(message.photoUrl).circleCrop().into(binding.messengerImageView)
            Glide.with(binding.root).load(message.imageUrl).into(binding.messageImageView)
        }

        companion object {
            fun from(parent: ViewGroup): ChatImageViewHolder {
                val binding =
                    ItemImageChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return ChatImageViewHolder(binding)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_TEXT = 1
        const val VIEW_TYPE_IMAGE = 2
    }
}