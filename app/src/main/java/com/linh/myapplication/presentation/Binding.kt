package com.linh.myapplication.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter("app:imageUrl")
fun setImageUrl(image: ImageView, url: String?) {
    if (url != null) {
        Glide.with(image).load(url).into(image)
    }
}

@BindingAdapter("app:timestamp")
fun setTimestamp(textView: TextView, timestamp: Long?) {
    if (timestamp != null) {
        val cal = Calendar.getInstance()
        cal.timeInMillis  = timestamp
        textView.text = "${cal.get(Calendar.DATE)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
    }
}