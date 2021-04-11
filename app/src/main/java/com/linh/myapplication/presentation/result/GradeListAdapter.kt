package com.linh.myapplication.presentation.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linh.myapplication.R
import com.linh.myapplication.domain.Result

class GradeListAdapter(private val context: Context) : RecyclerView.Adapter<GradeListAdapter.GradeViewHolder>() {
    private val list = mutableListOf<Result>()

    fun submitList(list: List<Result>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false)
        return GradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val item = list[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    class GradeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Result) {
            view.findViewById<TextView>(R.id.text_result_name).text = item.name
            view.findViewById<TextView>(R.id.text_result_grade).text = item.grade
            view.findViewById<TextView>(R.id.text_result_point).text = String.format("%.1f", item.point)
        }
    }
}