package com.example.eventposter.app.ui

import androidx.lifecycle.ViewModel
import com.example.eventposter.data.repository.EventRepositoryImpl
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.usecase.GetEventByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FormEventViewModel: ViewModel() {

    private val repository = EventRepositoryImpl.getInstance()
    private val getEventByIdUseCase = GetEventByIdUseCase(repository)

    private var _eventId = MutableStateFlow(0)
    val eventId: StateFlow<Int> = _eventId

    val event: Flow<EventModel?> = getEventByIdUseCase.launch(eventId)

    fun setEventId(id: Int) {
        _eventId.update { id }
    }

    fun saveEvent(event: EventModel) {

    }

}