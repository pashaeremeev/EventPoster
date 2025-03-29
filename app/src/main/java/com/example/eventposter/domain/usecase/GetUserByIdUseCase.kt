package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.UserModel
import com.example.eventposter.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(
    private val repository: UserRepository
) {

    fun launch(id: Flow<Int>): Flow<UserModel?> {
        return repository.getUserFlowById(id)
    }

}