package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByIdUseCase(
    private val repository: EventRepository
) {

    fun launch(id: Int): Flow<EventModel?> {
        return repository.getEventFlowById(id)
    }

}