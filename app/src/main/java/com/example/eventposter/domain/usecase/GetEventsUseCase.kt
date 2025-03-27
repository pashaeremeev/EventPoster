package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(
    private val repository: EventRepository
) {

    fun launch(): Flow<List<EventModel>> {
        return repository.getEventsFlow()
    }

}