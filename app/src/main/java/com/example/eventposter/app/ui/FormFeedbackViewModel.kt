package com.example.eventposter.app.ui

import androidx.lifecycle.ViewModel
import com.example.eventposter.data.repository.FeedbackRepositoryImpl
import com.example.eventposter.data.repository.UserRepositoryImpl
import com.example.eventposter.domain.usecase.CreateFeedbackUseCase

class FormFeedbackViewModel: ViewModel() {

    private val feedbackRepository = FeedbackRepositoryImpl.getInstance()
    private val userRepository = UserRepositoryImpl.getInstance()
    private val createFeedbackUseCase = CreateFeedbackUseCase(
        feedbackRepository,
        userRepository
    )

    fun createFeedback(
        userId: Int,
        isAnonymous: Boolean,
        rating: Float,
        text: String,
        eventId: Int
    ) {
        createFeedbackUseCase.launch(
            userId = userId,
            rating = rating,
            text = text.trim(),
            eventId = eventId,
            isAnonymous = isAnonymous
        )
    }
}