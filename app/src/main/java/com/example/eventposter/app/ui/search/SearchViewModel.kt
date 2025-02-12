package com.example.eventposter.app.ui.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SearchViewModel : ViewModel() {

    private val _text = MutableStateFlow("")
    val text: Flow<String> = _text

    fun setSearchSettings(text: String) {
        _text.value = text
    }
}