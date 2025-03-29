package com.example.eventposter.app.ui.event.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.eventposter.data.repository.EventRepositoryImpl
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.usecase.GetActiveEventsUseCase
import com.example.eventposter.domain.usecase.SearchEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class EventsSearchViewModel : ViewModel() {

    private val repository = EventRepositoryImpl.getInstance()
    private val searchEventsUseCase = SearchEventsUseCase(repository)

    private var _searchFilter = MutableStateFlow(FilterEventModel())
    val searchFilter: StateFlow<FilterEventModel> = _searchFilter

    fun updateFilter(update: FilterEventModel.() -> FilterEventModel) {
        _searchFilter.update { it.update() }
    }

    val events: Flow<List<EventModel>> = searchEventsUseCase.launch(searchFilter)
}