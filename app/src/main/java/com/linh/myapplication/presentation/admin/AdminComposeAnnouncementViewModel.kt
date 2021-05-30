package com.linh.myapplication.presentation.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.myapplication.data.remote.announcement.AnnouncementService
import com.linh.myapplication.domain.Announcement
import com.linh.myapplication.presentation.CalendarUtl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AdminComposeAnnouncementViewModel(private val service: AnnouncementService) : ViewModel() {
    val title = MutableLiveData("")
    val url = MutableLiveData("")
    val content = MutableLiveData("")

    val titleError = MutableLiveData(false)
    val urlError = MutableLiveData(false)
    val contentError = MutableLiveData(false)

    fun sendAnnouncement() {
        viewModelScope.launch {
            if (isValidInput()) {
                withContext(Dispatchers.IO) {
                    service.addAnnouncement(
                        Announcement(
                            0,
                            url.value!!,
                            title.value!!,
                            content.value!!,
                            Calendar.getInstance().timeInMillis
                        )
                    )
                }

            }
        }
    }

    private fun isValidInput(): Boolean {
        titleError.value = title.value.isNullOrEmpty()
        urlError.value = url.value.isNullOrEmpty()
        contentError.value = content.value.isNullOrEmpty()
        return !(title.value.isNullOrEmpty() || url.value.isNullOrEmpty() || content.value.isNullOrEmpty())
    }
}