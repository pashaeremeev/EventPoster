package com.example.eventposter.domain.model

import com.example.eventposter.data.entity.Feedback
import java.util.Date

data class FeedbackModel (
    val userName: String,
    val date: Date,
    val rating: Float,
    val text: String,
    val eventId: Int
) {
    fun toData(): Feedback = Feedback(
        userName = userName,
        date = date,
        rating = rating,
        text = text,
        eventId = eventId
    )
}