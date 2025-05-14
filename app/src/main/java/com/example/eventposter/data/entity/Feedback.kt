package com.example.eventposter.data.entity

import com.example.eventposter.domain.model.FeedbackModel
import java.util.Date

data class Feedback (
    val userName: String,
    val date: Date,
    val rating: Float,
    val text: String,
    val eventId: Int,
    val isAnonymous: Boolean = false
) {
    fun toModel(): FeedbackModel = FeedbackModel(
        userName = userName,
        date = date,
        rating = rating,
        text = text,
        eventId = eventId,
        isAnonymous = isAnonymous
    )
}