package com.example.eventposter.domain.model

import com.example.eventposter.app.ui.FilterModel
import com.example.eventposter.data.storage.FilterEvent
import java.util.Calendar
import java.util.Date
import kotlin.math.min

data class FilterEventModel(
    var text: String = "",
    var startDate: Date = Calendar.getInstance().time,
    var endDate: Date? = null,
    var categories: MutableMap<String, Boolean> = mutableMapOf(),
    var minPrice: Float = 0f,
    var maxPrice: Float = 10000f,
    var radius: Int = 10
): FilterModel() {

    fun toData(): FilterEvent {
        return FilterEvent(
            text = text,
            startDate = startDate,
            endDate = endDate,
            categories = categories,
            minPrice = minPrice,
            maxPrice = maxPrice,
            radius = radius
        )
    }

}