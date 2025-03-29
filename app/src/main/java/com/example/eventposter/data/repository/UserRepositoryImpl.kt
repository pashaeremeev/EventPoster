package com.example.eventposter.data.repository

import com.example.eventposter.data.storage.UserStorage
import com.example.eventposter.domain.model.FilterUserModel
import com.example.eventposter.domain.model.UserModel
import com.example.eventposter.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl: UserRepository {

    companion object {
        private var repository: UserRepositoryImpl? = null
        fun getInstance(): UserRepositoryImpl {
            if (repository == null) {
                repository = UserRepositoryImpl()
            }
            return repository!!
        }
    }

    private val storage = UserStorage.getInstance()

    override fun getUsersFlow(): Flow<List<UserModel>> {
        return storage.getUsersFlow().map { users ->
            users.map { user -> user.toModel() }
        }
    }

    override fun getUserFlowById(idFlow: Flow<Int>): Flow<UserModel?> {
        return storage.getUserFlowById(idFlow).map { user -> user?.toModel() }
    }

    override fun getUsersFlowByFilter(filterFlow: Flow<FilterUserModel>): Flow<List<UserModel>> {
        val filterFlowToData = filterFlow.map { filter -> filter.toData() }
        return storage.getUsersFlowByFilter(filterFlowToData).map { users ->
            users.map { user -> user.toModel() }
        }
    }


}