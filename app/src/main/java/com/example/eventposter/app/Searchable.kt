package com.example.eventposter.app

import com.example.eventposter.app.ui.FilterSettingsModel

interface Searchable {

    fun onFilterChanged(newSettings: FilterSettingsModel)

    fun onFilterChanged(newText: String)
}