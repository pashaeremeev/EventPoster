package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class SearchEventsUseCase(
    private val repository: EventRepository
) {

    fun launch(filter: Flow<FilterEventModel>): Flow<List<EventModel>> {
        return repository.getEventsFlowByFilter(filter)
    }

}