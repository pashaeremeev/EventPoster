package com.example.eventposter.domain.usecase

import com.example.eventposter.domain.model.FilterUserModel
import com.example.eventposter.domain.model.UserModel
import com.example.eventposter.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SearchUsersUseCase(
    private val repository: UserRepository
) {

    fun launch(filter: Flow<FilterUserModel>): Flow<List<UserModel>> {
        return repository.getUsersFlowByFilter(filter)
    }

}