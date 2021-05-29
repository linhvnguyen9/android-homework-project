package com.linh.myapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linh.myapplication.databinding.ItemAnnouncementBinding
import com.linh.myapplication.domain.Announcement

class AnnouncementsListAdapter(private val clickListener : AnnouncementClickListener) :
    ListAdapter<Announcement, AnnouncementsListAdapter.AnnouncementViewHolder>(AnnouncementDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder =
        AnnouncementViewHolder.from(parent)

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, clickListener)
    }

    class AnnouncementViewHolder(private val binding: ItemAnnouncementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Announcement, clickListener: AnnouncementClickListener) {
            binding.root.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.announcement = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AnnouncementViewHolder {
                return AnnouncementViewHolder(
                    ItemAnnouncementBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        )
                    )
                )
            }
        }
    }
}

class AnnouncementDiffUtil : DiffUtil.ItemCallback<Announcement>() {
    override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement): Boolean =
        oldItem == newItem
}

interface AnnouncementClickListener {
    fun onClick(announcement: Announcement)
}