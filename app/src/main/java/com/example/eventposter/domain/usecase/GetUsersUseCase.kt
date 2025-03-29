package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.UserModel
import com.example.eventposter.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: UserRepository
) {

    fun launch(): Flow<List<UserModel>> {
        return repository.getUsersFlow()
    }
}