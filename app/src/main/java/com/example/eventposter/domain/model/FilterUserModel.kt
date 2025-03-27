package com.example.eventposter.domain.model

import com.example.eventposter.app.ui.FilterModel

data class FilterUserModel(
    var name: String = "",
    var age: Int? = null
): FilterModel()