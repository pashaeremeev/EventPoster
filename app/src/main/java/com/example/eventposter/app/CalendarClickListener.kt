package com.example.eventposter.app

import java.util.Date

interface CalendarClickListener {

    fun invoke(date: Date)
}