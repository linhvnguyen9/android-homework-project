package com.linh.myapplication.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(@PrimaryKey val id: Int, val name: String, val type: String, val timestamp: Long, val isCancelled: Boolean, val roomName: String, val teacherName: String)

enum class ScheduleType {
    TEST,
    LEARNING,
    EVENT
}