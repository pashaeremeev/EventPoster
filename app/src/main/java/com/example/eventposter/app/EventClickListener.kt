package com.example.eventposter.app

import com.example.eventposter.domain.model.EventModel

interface EventClickListener {

    fun invoke(event: EventModel?)
}