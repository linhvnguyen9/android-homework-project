package com.linh.myapplication

import android.app.Application
import com.linh.myapplication.di.ourModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StudentManagementApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@StudentManagementApplication)
            modules(
                ourModule
            )
        }
    }
}