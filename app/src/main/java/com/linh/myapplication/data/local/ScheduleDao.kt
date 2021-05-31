package com.linh.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.domain.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    fun getAll() : List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(announcements: List<Schedule>)
}