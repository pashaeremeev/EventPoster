package com.example.eventposter.data.entity

import com.example.eventposter.domain.model.UserModel

data class User(
    val id: Int,
    val name: String,
    val urlIcon: String,
    val age: Int
) {
    fun toModel(): UserModel {
        return UserModel(
            id = id,
            name = name,
            urlIcon = urlIcon,
            age = age
        )
    }
}