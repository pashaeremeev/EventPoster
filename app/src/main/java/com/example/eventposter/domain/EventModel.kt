package com.example.eventposter.domain

import java.util.Date

data class EventModel(
     val name: String,
     val address: String,
     val startDate: Date,
     val endDate: Date,
     val posterUrl: String?
) {

}