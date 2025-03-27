package com.example.eventposter.data.storage

import com.example.eventposter.data.entity.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date

class EventStorage {

    companion object {
        private const val DAY_IN_MILLIS = 24 * 60 * 60 * 1000L
        private val cal: Calendar = Calendar.getInstance()
        private var storage: EventStorage? = null
        fun getInstance(): EventStorage {
            if (storage == null) {
                storage = EventStorage()
            }
            return storage!!
        }
    }

    private val events = listOf(
        Event(
            id = 1,
            name = "Новый год на Красной Площади",
            address = "г. Чебоксары, Красная Площадь",
            startDate = Date(cal.timeInMillis - DAY_IN_MILLIS * 2),
            endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
            posterUrl = "https://fs01.cap.ru/www22-09/gcheb/news/2023/01/18/9d4048df-fdf8-4300-a022-336e28ccc8f1/zaliv.jpg"
        ),
        Event(
            id = 2,
            name = "Раздача алмазов",
            address = "г. Москва, Красная Площадь",
            startDate = Date(cal.timeInMillis - DAY_IN_MILLIS * 1),
            endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 3),
            posterUrl = "https://kultura.orb.ru/uploads/images/afisha/2023/12/afisha_13020_0.jpg"
        ),
        Event(
            id = 3,
            name = "Путешествие в Америку",
            address = "г. Архангельск, Порт",
            startDate = Date(cal.timeInMillis - DAY_IN_MILLIS * 1),
            endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 120),
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjA0ZDlkNzMtYjVlNS00MWY2LWE3N2ItMDZlMDEwNWU2N2M5XkEyXkFqcGdeQXVyMzY0MTE3NzU@._V1_.jpg"
        ),
        Event(
            id = 4,
            name = "Приглашение в гости",
            address = "г. Чебоксары, пр. Мира, д. 48",
            startDate = Date(cal.timeInMillis - (DAY_IN_MILLIS / 2)),
            endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
            posterUrl = null
        )
    )

    fun getEventsFlow(): Flow<List<Event>> = flow { emit(events) }

    fun getActiveEventsFlow(dateFlow: Flow<Date>): Flow<List<Event>> = getEventsFlow().combine(dateFlow) {
            events, date ->
        val eventsFilteredByDate = mutableListOf<Event>()
        for (event in events) {
            val eventFilteredByDate = if (event.startDate.rangeTo(event.endDate).contains(date)
                || event.startDate.time.div(DAY_IN_MILLIS) == date.time.div(DAY_IN_MILLIS)) event
                else null
            eventFilteredByDate?.let { eventsFilteredByDate.add(it) }
        }
        return@combine eventsFilteredByDate
    }

    fun getEventFlowById(id: Int): Flow<Event?> = getEventsFlow().map {
        events -> events.firstOrNull { event -> event.id == id }
    }

    fun getEventsByFilter(filterFlow: Flow<FilterEvent>): Flow<List<Event>> {
        val result = getEventsFlow().combine(filterFlow) { events, filter ->
            val newEvents = events.filter { event ->

                val coversName = event.name.lowercase().contains(filter.text.lowercase())

                val coversAddress = event.address.lowercase().contains(filter.text.lowercase())

                val coversDates = filter.endDate?.let {
                    event.startDate.time <= filter.endDate!!.time
                            && event.endDate.time >= filter.startDate.time
                } ?: (event.startDate.time <= filter.startDate.time
                        && filter.startDate.time <= event.endDate.time)

//                val priceInRange = event.minPrice in filter.minPrice..filter.maxPrice

                coversDates && (coversName || coversAddress) //&& priceInRange
            }
            return@combine newEvents
        }
        return result
    }
}