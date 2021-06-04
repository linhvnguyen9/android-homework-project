package com.linh.myapplication.presentation.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.myapplication.data.local.ScheduleDao
import com.linh.myapplication.data.remote.schedule.ScheduleService
import com.linh.myapplication.domain.Schedule
import com.linh.myapplication.presentation.CalendarUtl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ScheduleViewModel(private val service: ScheduleService, private val dao: ScheduleDao) : ViewModel() {
    var query: String = ""
    var sort: String = ""
    var timeLower: Long = 0L
    var timeUpper: Long = 1722781439281L

    val schedule: LiveData<List<Schedule>> get() = _schedule
    private val _schedule = MutableLiveData<List<Schedule>>()

    init {
        getSchedule()
    }

    //Null = don't change query, blank = change query to get all
    //One function to rule them all!!!
    fun getSchedule(
        searchQuery: String? = "",
        sortKey: String? = "timestamp",
        timestampLower: Long? = 0,
        timestampUpper: Long? = 1722781439281L
    ) {
        if (searchQuery != null) {
            query = searchQuery
        }
        if (sortKey != null) {
            sort = sortKey
        }
        if (timestampLower != null) {
            timeLower = timestampLower
        }
        if (timestampUpper != null) {
            timeUpper = timestampUpper
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _schedule.postValue(dao.getAll("%${query}%", sort, timeLower, timeUpper))
                val apiResponse = service.getSchedule()

                if (apiResponse.isSuccessful()) {
                    dao.insert(apiResponse.data!!)
                    _schedule.postValue(dao.getAll("%${query}%", sort, timeLower, timeUpper))
                }
            }
        }
    }
}