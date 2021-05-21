package com.linh.myapplication.data.remote.announcement

import com.linh.myapplication.domain.Announcement
import retrofit2.http.GET

interface AnnouncementService {
    @GET("/announcements")
    suspend fun getAnnouncements() : List<Announcement>
}