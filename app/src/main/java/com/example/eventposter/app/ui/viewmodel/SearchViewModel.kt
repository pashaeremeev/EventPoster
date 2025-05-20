package com.example.eventposter.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.model.FilterUserModel

class SearchViewModel : ViewModel() {

    var eventFilter = FilterEventModel()
    var userFilter = FilterUserModel()
    var lastTab = 0
}