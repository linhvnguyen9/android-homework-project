package com.linh.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.domain.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule WHERE timestamp >= :timestampLower AND timestamp <= :timestampUpper AND name LIKE :nameQuery ORDER BY CASE WHEN :sortKey = 'time' THEN timestamp END ASC, CASE WHEN :sortKey = 'name' THEN name END ASC, CASE WHEN :sortKey = 'teacher' THEN teacherName END ASC")
    fun getAll(
        nameQuery: String,
        sortKey: String,
        timestampLower: Long,
        timestampUpper: Long
    ): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(announcements: List<Schedule>)
}