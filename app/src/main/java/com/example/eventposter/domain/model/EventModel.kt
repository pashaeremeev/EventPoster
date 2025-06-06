package com.example.eventposter.domain.model

import java.util.Date

data class EventModel(
     val id: Int,
     val name: String,
     val address: String,
     val startDate: Date,
     val endDate: Date?,
     val posterUrl: String?,
     val keywords: List<String> = listOf(),
     val price: Double = 0.00
)