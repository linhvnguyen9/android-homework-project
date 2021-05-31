package com.linh.myapplication.presentation.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.myapplication.data.local.ScheduleDao
import com.linh.myapplication.data.remote.schedule.ScheduleService
import com.linh.myapplication.domain.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScheduleViewModel(private val service: ScheduleService, private val dao: ScheduleDao) : ViewModel() {
    val schedule : LiveData<List<Schedule>> get() = _schedule
    private val _schedule = MutableLiveData<List<Schedule>>()

    init {
        getSchedule()
    }

    private fun getSchedule() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _schedule.postValue(dao.getAll())
                val apiResponse = service.getSchedule()

                if (apiResponse.isSuccessful()) {
                    dao.insert(apiResponse.data!!)
                    _schedule.postValue(apiResponse.data!!)
                }
            }
        }
    }
}