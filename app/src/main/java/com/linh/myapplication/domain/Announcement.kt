package com.linh.myapplication.domain

data class Announcement(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val content: String,
    val timestamp: Long
)