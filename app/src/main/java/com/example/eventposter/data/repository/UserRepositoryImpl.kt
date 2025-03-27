package com.example.eventposter.data.repository

import com.example.eventposter.data.entity.User
import com.example.eventposter.domain.repository.UserRepository

class UserRepositoryImpl: UserRepository {

    private val userList = mutableListOf<User>()

    override fun getUserList(): List<User> {
        return userList
    }

    override fun getUserById(id: Int): User? {
        return userList.firstOrNull{ it.id == id }
    }
}