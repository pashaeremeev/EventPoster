package com.example.eventposter.data.repository

import com.example.eventposter.data.storage.FeedbackStorage
import com.example.eventposter.domain.model.FeedbackModel
import com.example.eventposter.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedbackRepositoryImpl: FeedbackRepository {

    companion object {
        private var repository: FeedbackRepositoryImpl? = null
        fun getInstance(): FeedbackRepositoryImpl {
            if (repository == null) {
                repository = FeedbackRepositoryImpl()
            }
            return repository!!
        }
    }

    private val storage = FeedbackStorage.getInstance()

    override fun addFeedback(feedback: FeedbackModel) {
        storage.addFeedback(feedback.toData())
    }

    override fun getEventFeedbacksFlow(eventId: Flow<Int>): Flow<List<FeedbackModel>> {
        return storage.getEventFeedbacksFlow(eventId).map {
            feedbacks -> feedbacks.map {
                  feedback -> feedback.toModel()
            }
        }
    }

    override fun clear() {
        storage.clear()
    }


}