package com.example.eventposter.domain.model

import java.util.Date

data class TicketModel(
    val id: Int = 0,
    val eventId: Int,
    val userId: Int,
    val purchaseDate: Date,
    val statusId: Int,
    val ticketCode: String,
    val price: Double,
    val ticketType: String
)