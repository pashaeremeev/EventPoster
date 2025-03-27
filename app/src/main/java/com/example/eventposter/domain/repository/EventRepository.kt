package com.example.eventposter.domain.repository

import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FilterEventModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface EventRepository {

    fun getEventsFlow(): Flow<List<EventModel>>

    fun getActiveEventsFlow(dateFlow: Flow<Date>): Flow<List<EventModel>>

    fun getEventFlowById(id: Int): Flow<EventModel?>

    fun getEventsFlowByFilter(filter: Flow<FilterEventModel>): Flow<List<EventModel>>
}