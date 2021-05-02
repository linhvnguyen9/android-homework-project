package com.linh.myapplication

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import timber.log.Timber

class StudentManagementApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        FirebaseDatabase.getInstance().apply {
            setLogLevel(Logger.Level.DEBUG)
            setPersistenceEnabled(true)
        }
    }
}