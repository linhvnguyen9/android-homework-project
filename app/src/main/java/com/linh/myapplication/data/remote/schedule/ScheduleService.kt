package com.linh.myapplication.data.remote.schedule

import com.linh.myapplication.data.remote.announcement.Resource2
import com.linh.myapplication.domain.Schedule
import retrofit2.http.GET

interface ScheduleService {
    @GET("/schedule")
    suspend fun getSchedule() : Resource2<List<Schedule>>
}