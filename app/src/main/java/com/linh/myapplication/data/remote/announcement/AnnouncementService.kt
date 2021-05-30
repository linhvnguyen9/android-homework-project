package com.linh.myapplication.data.remote.announcement

import com.linh.myapplication.domain.Announcement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnnouncementService {
    @GET("/announcements")
    suspend fun getAnnouncements() : Resource2<List<Announcement>>

    @POST("/announcements")
    suspend fun addAnnouncement(@Body announcement: Announcement): Response<Any>
}