package com.example.eventposter.data.entity

import com.example.eventposter.domain.model.EventModel
import java.util.Date

data class Event(
    val id: Int,
    val name: String,
    val address: String,
    val startDate: Date,
    val endDate: Date,
    val posterUrl: String?
) {
    fun toModel(): EventModel {
        return EventModel(
            id = id,
            name = name,
            address = address,
            startDate = startDate,
            endDate = endDate,
            posterUrl = posterUrl
        )
    }
}