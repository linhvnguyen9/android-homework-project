package com.linh.myapplication.presentation.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linh.myapplication.R
import com.linh.myapplication.databinding.ItemScheduleBinding
import com.linh.myapplication.domain.Schedule

class ScheduleListAdapter(private val context: Context) :
    ListAdapter<Schedule, ScheduleListAdapter.ScheduleViewHolder>(ScheduleDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    class ScheduleViewHolder(private val binding: ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Schedule) {
            binding.schedule = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ScheduleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemScheduleBinding.inflate(layoutInflater, parent, false)
                return ScheduleViewHolder(binding)
            }
        }
    }
}

class ScheduleDiffUtil : DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule) = oldItem == newItem
}