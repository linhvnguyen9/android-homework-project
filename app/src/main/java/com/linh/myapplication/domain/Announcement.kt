package com.linh.myapplication.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Announcement(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val imageUrl: String,
    val title: String,
    val content: String,
    val timestamp: Long
) : Parcelable