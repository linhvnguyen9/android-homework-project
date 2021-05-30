package com.linh.myapplication.di

import com.linh.myapplication.data.local.AppDatabase
import com.linh.myapplication.data.remote.announcement.AnnouncementService
import com.linh.myapplication.presentation.admin.AdminComposeAnnouncementViewModel
import com.linh.myapplication.presentation.home.HomeViewModel
import get
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val ourModule = module {
    factory {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    factory {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("http://192.168.1.113:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(AnnouncementService::class.java) }

    factory { get<AppDatabase>().announcementDao() }
    single { AppDatabase.getInstance(androidApplication()) }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { AdminComposeAnnouncementViewModel(get()) }
}