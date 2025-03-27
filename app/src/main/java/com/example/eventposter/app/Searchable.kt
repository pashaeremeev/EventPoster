package com.example.eventposter.app

import com.example.eventposter.app.ui.FilterModel

interface Searchable {

    fun onFilterChanged(newSettings: FilterModel)

    fun onFilterChanged(newText: String)
}