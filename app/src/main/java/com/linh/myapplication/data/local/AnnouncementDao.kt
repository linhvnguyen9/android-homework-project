package com.linh.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.linh.myapplication.domain.Announcement

@Dao
interface AnnouncementDao {
    @Query ("SELECT * FROM announcement")
    fun getAll() : List<Announcement>

    @Insert
    fun insert(announcements: List<Announcement>)
}