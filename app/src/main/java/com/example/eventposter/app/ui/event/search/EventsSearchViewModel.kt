package com.example.eventposter.app.ui.event.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.eventposter.domain.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class EventsSearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: Flow<String> = _searchQuery

    fun setSearchQuery(text: String) {
        _searchQuery.value = text
    }

    private val _events = MutableStateFlow<List<EventModel>>(listOf())
    val events: LiveData<List<EventModel>>
        get() = _events.combine(searchQuery) {
                events, text ->
            val eventsFilteredByName = mutableListOf<EventModel>()
            for (i in events.indices) {
                val event = events[i]
                if (event.name.lowercase().contains(text.lowercase())
                    || event.address.lowercase().contains(text.lowercase())) {
                    eventsFilteredByName.add(event)
                }
            }
            return@combine eventsFilteredByName
        }.asLiveData(context = Dispatchers.IO)

    fun setEvents(events: List<EventModel>) {
        _events.value = events
    }
}