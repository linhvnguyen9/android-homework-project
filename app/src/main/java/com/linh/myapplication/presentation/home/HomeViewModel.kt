package com.linh.myapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.myapplication.data.local.AnnouncementDao
import com.linh.myapplication.data.remote.announcement.AnnouncementService
import com.linh.myapplication.domain.Announcement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val announcementService: AnnouncementService, private val announcementDao: AnnouncementDao) : ViewModel() {
    val announcements : LiveData<List<Announcement>> get() = _announcements
    private val _announcements = MutableLiveData<List<Announcement>>()

    init {
        getAnnouncements()
    }

    private fun getAnnouncements() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _announcements.postValue(announcementDao.getAll())
                val apiResponse = announcementService.getAnnouncements()

                if (apiResponse.isSuccessful()) {
                    announcementDao.insert(apiResponse.data!!)
                    _announcements.postValue(apiResponse.data!!)
                }
            }
        }
    }
}