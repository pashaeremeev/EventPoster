package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetActiveEventsUseCase(
    private val repository: EventRepository
) {

    fun launch(dateFlow: Flow<Date>): Flow<List<EventModel>> {
        return repository.getActiveEventsFlow(dateFlow)
    }

}