package com.linh.myapplication.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.linh.myapplication.R
import com.linh.myapplication.domain.ScheduleType
import java.util.*

@BindingAdapter("app:imageUrl")
fun setImageUrl(image: ImageView, url: String?) {
    if (url != null) {
        Glide.with(image).load(url).into(image)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, isVisible: Boolean?) {
    if (isVisible != null) {
        view.isVisible = isVisible
    }
}

@BindingAdapter("app:datetime")
fun setVisibility(view: TextView, timestamp: Long?) {
    if (timestamp != null) {
        view.text = CalendarUtl.toFormattedTime(timestamp)
    }
}

@BindingAdapter("app:scheduletype")
fun setScheduleType(view: ImageView, type: String?) {
    val enum = ScheduleType.valueOf(type!!.toUpperCase())
    if (enum != null) {
        when (enum) {
            ScheduleType.TEST -> {
                view.setImageResource(R.drawable.ic_baseline_assignment_24)
            }
            ScheduleType.LEARNING -> {
                view.setImageResource(R.drawable.ic_baseline_assignment_24)
            }
            ScheduleType.EVENT -> {
                view.setImageResource(R.drawable.ic_baseline_event_24)
            }
        }
    }
}

@BindingAdapter("app:timestamp")
fun setTimestamp(textView: TextView, timestamp: Long?) {
    if (timestamp != null) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        textView.text =
            "${cal.get(Calendar.DATE)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
    }
}