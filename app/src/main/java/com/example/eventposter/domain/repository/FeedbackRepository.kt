package com.example.eventposter.domain.repository

import com.example.eventposter.domain.model.FeedbackModel
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {

    fun addFeedback(feedback: FeedbackModel)

    fun getEventFeedbacksFlow(eventId: Flow<Int>): Flow<List<FeedbackModel>>

    fun clear()

}