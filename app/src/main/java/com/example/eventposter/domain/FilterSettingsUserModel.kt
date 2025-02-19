package com.example.eventposter.domain

import com.example.eventposter.app.ui.FilterSettingsModel

data class FilterSettingsUserModel(
    var name: String = "",
    var age: Int? = null
): FilterSettingsModel()