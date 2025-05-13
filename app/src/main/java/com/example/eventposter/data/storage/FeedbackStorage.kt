package com.example.eventposter.data.storage

import com.example.eventposter.data.entity.Feedback
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import java.util.Date

class FeedbackStorage {

    companion object {
        private var storage: FeedbackStorage? = null
        fun getInstance(): FeedbackStorage {
            if (storage == null) {
                storage = FeedbackStorage()
            }
            return storage!!
        }
    }

    private val feedbacks = mutableListOf(
        Feedback(
            userName = "Алексей Петров",
            date = Date(),
            rating = 4.5f,
            text = "Отличное мероприятие, всё понравилось!",
            eventId = 2
        ),
        Feedback(
            userName = "Мария Иванова",
            date = Date(),
            rating = 3.0f,
            text = "Могло быть лучше, мало активности",
            eventId = 1
        ),
        Feedback(
            userName = "Дмитрий Сидоров",
            date = Date(),
            rating = 5.0f,
            text = "Лучшее событие года! Спасибо организаторам!",
            eventId = 1
        )
    )

    fun addFeedback(feedback: Feedback) {
        feedbacks.add(feedback)
    }

    private fun getAllFeedbacksFlow(): Flow<List<Feedback>> = flow { emit(feedbacks) }

    fun getEventFeedbacksFlow(eventId: Flow<Int>): Flow<List<Feedback>> = getAllFeedbacksFlow().combine(eventId) {
        feedbacks, eventId ->
        return@combine feedbacks.filter { it.eventId == eventId }
    }

    fun clear() {
        feedbacks.clear()
    }

}