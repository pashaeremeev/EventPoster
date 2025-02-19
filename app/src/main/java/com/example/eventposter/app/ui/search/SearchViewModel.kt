package com.example.eventposter.app.ui.search

import androidx.lifecycle.ViewModel
import com.example.eventposter.app.ui.FilterSettingsModel
import com.example.eventposter.domain.FilterSettingsEventModel
import com.example.eventposter.domain.FilterSettingsUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SearchViewModel : ViewModel() {

    var eventFilter = FilterSettingsEventModel()
    var userFilter = FilterSettingsUserModel()
}