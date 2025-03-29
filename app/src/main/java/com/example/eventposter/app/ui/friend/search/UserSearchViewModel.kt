package com.example.eventposter.app.ui.friend.search

import androidx.lifecycle.ViewModel
import com.example.eventposter.data.repository.UserRepositoryImpl
import com.example.eventposter.domain.model.FilterUserModel
import com.example.eventposter.domain.model.UserModel
import com.example.eventposter.domain.usecase.SearchUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UserSearchViewModel : ViewModel() {

    private val repository = UserRepositoryImpl.getInstance()
    private val searchUsersUseCase = SearchUsersUseCase(repository)

    private var _searchFilter = MutableStateFlow(FilterUserModel())
    val searchFilter: StateFlow<FilterUserModel> = _searchFilter

    fun updateFilter(update: FilterUserModel.() -> FilterUserModel) {
        _searchFilter.update { it.update() }
    }

    val users: Flow<List<UserModel>> = searchUsersUseCase.launch(searchFilter)

}