package com.example.eventposter.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {

    companion object {
        const val LENGTH_OF_DAYS = 90
    }

    private val _days = MutableLiveData<List<Date>>()
    val days: LiveData<List<Date>>
        get() = _days

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date>
        get() = _selectedDate

    init {
        getDays()
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }

    private fun getDays() {
        val calendar = Calendar.getInstance()
        val dates = mutableListOf<Date>()
        for (i in 0 until LENGTH_OF_DAYS) {
            dates.add(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }
        _days.value = dates
    }
}
