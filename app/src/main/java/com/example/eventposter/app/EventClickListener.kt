package com.example.eventposter.app

import com.example.eventposter.domain.EventModel

interface EventClickListener {

    fun invoke(event: EventModel?)
}