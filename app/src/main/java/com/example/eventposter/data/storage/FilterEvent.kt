package com.example.eventposter.data.storage

import java.util.Calendar
import java.util.Date

data class FilterEvent(
    var text: String = "",
    var startDate: Date = Calendar.getInstance().time,
    var endDate: Date? = null,
    var categories: MutableMap<String, Boolean> = mutableMapOf(),
    var minPrice: Float = 0f,
    var maxPrice: Float = 10000f,
    var radius: Int = 10
)