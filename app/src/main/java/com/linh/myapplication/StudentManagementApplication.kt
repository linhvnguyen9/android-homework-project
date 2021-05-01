package com.linh.myapplication

import android.app.Application
import timber.log.Timber

class StudentManagementApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}