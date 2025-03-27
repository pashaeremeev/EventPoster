package com.example.eventposter.domain.repository

import com.example.eventposter.data.entity.User

interface UserRepository {

    fun getUserList(): List<User>

    fun getUserById(id: Int): User?
}