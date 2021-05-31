package com.linh.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.domain.Schedule

@Database(entities = [Announcement::class, Schedule::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun announcementDao() : AnnouncementDao
    abstract fun scheduleDao() : ScheduleDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(applicationContext: Context) : AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "studentmanagement.db").build()
            }
            return INSTANCE as AppDatabase
        }
    }
}