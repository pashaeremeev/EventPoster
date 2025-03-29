package com.example.eventposter.domain.repository

import com.example.eventposter.domain.model.FilterUserModel
import com.example.eventposter.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsersFlow(): Flow<List<UserModel>>

    fun getUserFlowById(id: Flow<Int>): Flow<UserModel?>

    fun getUsersFlowByFilter(filter: Flow<FilterUserModel>): Flow<List<UserModel>>
}