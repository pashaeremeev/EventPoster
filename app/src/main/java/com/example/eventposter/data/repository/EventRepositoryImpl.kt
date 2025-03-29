package com.example.eventposter.data.repository

import com.example.eventposter.data.storage.EventStorage
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class EventRepositoryImpl: EventRepository {

    companion object {
        private var repository: EventRepositoryImpl? = null
        fun getInstance(): EventRepositoryImpl {
            if (repository == null) {
                repository = EventRepositoryImpl()
            }
            return repository!!
        }
    }

    private val storage = EventStorage.getInstance()

    override fun getEventsFlow(): Flow<List<EventModel>> {
        return storage.getEventsFlow().map {
            events -> events.map { event ->  event.toModel() }
        }
    }

    override fun getActiveEventsFlow(dateFlow: Flow<Date>): Flow<List<EventModel>> {
        return storage.getActiveEventsFlow(dateFlow).map {
            events -> events.map { event -> event.toModel() }
        }
    }

    override fun getEventFlowById(idFlow: Flow<Int>): Flow<EventModel?> {
        return storage.getEventFlowById(idFlow).map { event -> event?.toModel() }
    }

    override fun getEventsFlowByFilter(filterFlow: Flow<FilterEventModel>): Flow<List<EventModel>> {
        val filterFlowToData = filterFlow.map { filter -> filter.toData() }
        return storage.getEventsByFilter(filterFlowToData).map { events ->
            events.map { event -> event.toModel()
            }
        }
    }

}