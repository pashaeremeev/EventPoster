package com.example.eventposter.app.ui.event.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.eventposter.domain.EventModel
import com.example.eventposter.domain.FilterSettingsEventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class EventsSearchViewModel : ViewModel() {

    private var _searchFilter = MutableStateFlow(FilterSettingsEventModel())
    val searchFilter: Flow<FilterSettingsEventModel> = _searchFilter

    fun updateFilter(update: FilterSettingsEventModel.() -> FilterSettingsEventModel) {
        _searchFilter.update { it.update() }
    }

    private val _events = MutableStateFlow<List<EventModel>>(listOf())
    val events: LiveData<List<EventModel>>
        get() = prepareEvents(
            eventsFlow = _events,
            filterFlow = searchFilter
        )

    fun setEvents(events: List<EventModel>) {
        _events.value = events
    }

    private fun prepareEvents(
        eventsFlow: Flow<List<EventModel>>,
        filterFlow: Flow<FilterSettingsEventModel>
    ): LiveData<List<EventModel>> {

        val result = eventsFlow.combine(filterFlow) { events, filter ->
            val newEvents = events.filter { event ->

                val coversName = event.name.lowercase().contains(filter.text.lowercase())

                val coversAddress = event.address.lowercase().contains(filter.text.lowercase())

                val coversDates = filter.endDate?.let {
                    event.startDate.time <= filter.endDate!!.time
                            && event.endDate.time >= filter.startDate.time
                } ?: (event.startDate.time <= filter.startDate.time
                        && filter.startDate.time <= event.endDate.time)

//                val priceInRange = event.minPrice in filter.minPrice..filter.maxPrice

                coversDates && (coversName || coversAddress) //&& priceInRange
            }
            return@combine newEvents
        }
        return result.asLiveData(context = Dispatchers.IO)
    }
}