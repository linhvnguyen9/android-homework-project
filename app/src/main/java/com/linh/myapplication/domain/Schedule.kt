package com.linh.myapplication.domain

data class Schedule(val id: Int, val name: String, val type: ScheduleType, val timestamp: Long, val isCancelled: Boolean, val roomName: String, val teacherName: String)

enum class ScheduleType {
    TEST,
    LEARNING,
    EVENT
}