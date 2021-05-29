package com.linh.myapplication.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Announcement(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val content: String,
    val timestamp: Long
) : Parcelable