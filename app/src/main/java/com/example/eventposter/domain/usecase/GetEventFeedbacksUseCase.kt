package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.FeedbackModel
import com.example.eventposter.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.Flow

class GetEventFeedbacksUseCase (
    private val repository: FeedbackRepository
) {

    fun launch(eventId: Flow<Int>): Flow<List<FeedbackModel>> {
        return repository.getEventFeedbacksFlow(eventId = eventId)
    }

}