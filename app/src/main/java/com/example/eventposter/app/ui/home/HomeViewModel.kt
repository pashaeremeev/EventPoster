package com.example.eventposter.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.eventposter.domain.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {

    companion object {
        const val LENGTH_OF_DAYS = 90
        const val DAY_IN_MILLIS: Long = 86_400_000
    }

    private val _days = MutableLiveData<List<Date>>()
    val days: LiveData<List<Date>>
        get() = _days

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date>
        get() = _selectedDate

    private val _events = MutableStateFlow<List<EventModel>>(listOf())
    val events: LiveData<List<EventModel>>
        get() = _events.combine(selectedDate.asFlow()) {
            events, date ->
            val eventsFilteredByDate = mutableListOf<EventModel>()
            for (i in events.indices) {
                val event = events[i]
                val eventFilteredByDate = if (event.startDate.rangeTo(event.endDate).contains(date)
                       || event.startDate.time.div(DAY_IN_MILLIS) == date.time.div(DAY_IN_MILLIS)) event else null
                eventFilteredByDate?.let { eventsFilteredByDate.add(it) }
            }
            return@combine eventsFilteredByDate
        }.asLiveData(context = Dispatchers.IO)

    init {
        getDays()
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }

    fun setEvents(events: List<EventModel>) {
        _events.value = events
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
