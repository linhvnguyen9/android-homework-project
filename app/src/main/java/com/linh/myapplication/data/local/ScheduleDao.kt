package com.linh.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.domain.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule WHERE name LIKE :nameQuery ORDER BY CASE WHEN :sortKey = 'time' THEN timestamp END ASC, CASE WHEN :sortKey = 'name' THEN name END ASC, CASE WHEN :sortKey = 'teacher' THEN teacherName END ASC")
    fun getAll(nameQuery: String, sortKey: String): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(announcements: List<Schedule>)
}