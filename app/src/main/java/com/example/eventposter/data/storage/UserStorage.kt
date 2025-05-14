package com.example.eventposter.data.storage

import com.example.eventposter.data.FilterUser
import com.example.eventposter.data.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class UserStorage {

    companion object {
        private var storage: UserStorage? = null
        fun getInstance(): UserStorage {
            if (storage == null) {
                storage = UserStorage()
            }
            return storage!!
        }
    }

    private val users = listOf(
        User(
            id = 1,
            name = "Павел Еремеев",
            urlIcon = "https://sun1-96.userapi.com/s/v1/ig2/KBdKwastFMxQ6k9HdHl49wD7UiUinUcoVb9_800NfAZ8r_Cg8u-XW-gFmwh5w3-CCc_pNwohVgs4RNp3FrsFytHV.jpg?quality=95&crop=848,251,801,801&as=32x32,48x48,72x72,108x108,160x160,240x240,360x360,480x480,540x540,640x640,720x720&ava=1&u=7NUvYU647bCq1G90YX55vnE2uy41oZLGPnZVWOwtDqI&cs=200x200",
            age = 18
        ),
        User(
            id = 2,
            name = "Егор Краснов",
            urlIcon = "https://sun1-28.userapi.com/s/v1/ig2/3nWI2TmzQM-aqZ5tCQRDYfj_al1xbOwhEzfCRQw6nYe6KKpYCh-LvatYpp_jv09aJCNmQrXL7naVrZSgCy34Qhjt.jpg?quality=95&crop=0,2,1077,1077&as=32x32,48x48,72x72,108x108,160x160,240x240,360x360,480x480,540x540,640x640,720x720&ava=1&cs=50x50",
            age = 17
        )
    )

    fun getUsersFlow(): Flow<List<User>> = flow { emit(users) }

    fun getUserFlowById(idFlow: Flow<Int>): Flow<User?> {
        val user = getUsersFlow().combine(idFlow) { users, id ->
            return@combine users.firstOrNull { it.id == id }
        }
        return user
    }

    fun getUsersFlowByFilter(filterFlow: Flow<FilterUser>): Flow<List<User>> {
        val result = getUsersFlow().combine(filterFlow) { users, filter ->
            val result = users.filter { user ->

                val coversName = user.name.lowercase().contains(filter.name.lowercase())

                val coversAge = filter.age?.let { user.age == it } ?: true

                coversName && coversAge
            }
            return@combine result
        }
        return result
    }

    fun getUserById(userId: Int): User? {
        return users.firstOrNull{ it.id == userId }
    }
}