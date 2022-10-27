package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.ScheduleDao

class BusScheduleViewModel (private val scheduleDao: ScheduleDao): ViewModel(){
    fun fullSchedule() = scheduleDao.getAll()
    fun scheduleForStopName(name: String) = scheduleDao.getByStopName(name)
}

// Instantiating viewModels in fragments directly is complex, otherwise fragment alone should take care of
// memory management
class BusScheduleViewModelFactory(
    private val scheduleDao: ScheduleDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusScheduleViewModel(scheduleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}