package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.FeedbackModel
import com.example.eventposter.domain.repository.FeedbackRepository
import com.example.eventposter.domain.repository.UserRepository
import java.util.Date

class CreateFeedbackUseCase(
    private val feedbackRepository: FeedbackRepository,
    private val userRepository: UserRepository
) {

    fun launch(userId: Int,
               isAnonymous: Boolean,
               rating: Float,
               text: String,
               eventId: Int) {
        val user = userRepository.getUserById(userId)
        return feedbackRepository.addFeedback(
            FeedbackModel(
                userName = user?.name ?: "",
                date = Date(),
                rating = rating,
                text = text.trim(),
                eventId = eventId,
                isAnonymous = isAnonymous
            )
        )
    }

}