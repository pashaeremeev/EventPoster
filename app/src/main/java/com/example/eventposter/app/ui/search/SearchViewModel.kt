package com.example.eventposter.app.ui.search

import androidx.lifecycle.ViewModel
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.model.FilterUserModel

class SearchViewModel : ViewModel() {

    var eventFilter = FilterEventModel()
    var userFilter = FilterUserModel()
}