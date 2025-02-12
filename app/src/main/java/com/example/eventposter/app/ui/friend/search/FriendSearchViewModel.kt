package com.example.eventposter.app.ui.friend.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.eventposter.domain.EventModel
import com.example.eventposter.domain.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class FriendSearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: Flow<String> = _searchQuery

    fun setSearchQuery(text: String) {
        _searchQuery.value = text
    }

    private val _users = MutableStateFlow<List<UserModel>>(listOf())
    val users: LiveData<List<UserModel>>
        get() = _users.combine(searchQuery) {
                users, text ->
            val usersFiltered = mutableListOf<UserModel>()
            for (i in users.indices) {
                val user = users[i]
                if (user.userName.contains(text.lowercase())) usersFiltered.add(user)
            }
            return@combine usersFiltered
        }.asLiveData(context = Dispatchers.IO)

    fun setUsers(users: List<UserModel>) {
        _users.value = users
    }
}