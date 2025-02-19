package com.example.eventposter.app.ui.friend.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.eventposter.domain.FilterSettingsUserModel
import com.example.eventposter.domain.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class UserSearchViewModel : ViewModel() {

    private var _searchFilter = MutableStateFlow(FilterSettingsUserModel())
    val searchFilter: Flow<FilterSettingsUserModel> = _searchFilter

    fun updateFilter(update: FilterSettingsUserModel.() -> FilterSettingsUserModel) {
        _searchFilter.update { it.update() }
    }

    private val _users = MutableStateFlow<List<UserModel>>(listOf())
    val users: LiveData<List<UserModel>>
        get() = prepareUsers(
            usersFlow = _users,
            filterFlow = searchFilter
        )

    fun setUsers(users: List<UserModel>) {
        _users.value = users
    }

    private fun prepareUsers(
        usersFlow: Flow<List<UserModel>>,
        filterFlow: Flow<FilterSettingsUserModel>
    ): LiveData<List<UserModel>> {
        return usersFlow.combine(filterFlow) {
                users, filter ->
            val result = users.filter { user ->

                val coversName = user.name.lowercase().contains(filter.name.lowercase())

                val coversAge = filter.age?.let { user.age == it } ?: true

                coversName && coversAge
            }
            return@combine result
        }.asLiveData(context = Dispatchers.IO)
    }

}