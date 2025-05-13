package com.example.eventposter.app.ui

import androidx.lifecycle.ViewModel
import com.example.eventposter.data.repository.EventRepositoryImpl
import com.example.eventposter.data.repository.FeedbackRepositoryImpl
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FeedbackModel
import com.example.eventposter.domain.usecase.GetEventByIdUseCase
import com.example.eventposter.domain.usecase.GetEventFeedbacksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class EventCardViewModel : ViewModel() {

    private val eventRepository = EventRepositoryImpl.getInstance()
    private val feedbackRepository = FeedbackRepositoryImpl.getInstance()
    private val getEventByIdUseCase = GetEventByIdUseCase(eventRepository)
    private val getEventFeedbacksUseCase = GetEventFeedbacksUseCase(feedbackRepository)

    private var _eventId = MutableStateFlow(0)
    val eventId: StateFlow<Int> = _eventId

    val event: Flow<EventModel?> = getEventByIdUseCase.launch(eventId)

    val feedbacks: Flow<List<FeedbackModel>> = getEventFeedbacksUseCase.launch(eventId)

    fun setEventId(id: Int) {
        _eventId.update { id }
    }

}