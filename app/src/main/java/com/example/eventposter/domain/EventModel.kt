package com.example.eventposter.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class EventModel(
     val id: Int,
     val name: String,
     val address: String,
     val startDate: Date,
     val endDate: Date,
     val posterUrl: String?
) : Parcelable