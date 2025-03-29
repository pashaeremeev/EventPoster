package com.example.eventposter.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.example.eventposter.data.repository.EventRepositoryImpl
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.usecase.GetActiveEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {

    companion object {
        const val LENGTH_OF_DAYS = 90
    }

    private val repository = EventRepositoryImpl.getInstance()
    private val getActiveEventsUseCase = GetActiveEventsUseCase(repository)

    private val _days = MutableLiveData<List<Date>>()
    val days: LiveData<List<Date>> = _days

    private val _selectedDate = MutableStateFlow(Date())
    val selectedDate: StateFlow<Date> = _selectedDate

    val events: Flow<List<EventModel>> = getActiveEventsUseCase
        .launch(selectedDate)

    init {
        getDays()
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.update { date }
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
