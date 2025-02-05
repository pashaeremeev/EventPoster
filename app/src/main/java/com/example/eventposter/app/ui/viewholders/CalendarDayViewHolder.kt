package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvCalendarDay = itemView.findViewById<TextView>(R.id.tvCalendarDay)
    private val tvCalendarDate = itemView.findViewById<TextView>(R.id.tvCalendarDate)
    private val cvCardCalendar = itemView.findViewById<MaterialCardView>(R.id.cvCardCalendar)

    fun getTvCalendarDay(): TextView {
        return tvCalendarDay
    }

    fun getTvCalendarDate(): TextView {
        return tvCalendarDate
    }

    fun getCvCardCalendar(): MaterialCardView {
        return cvCardCalendar
    }

    fun bind(date: Date) {
        var format = SimpleDateFormat("EE", Locale("ru", "RU"))
        tvCalendarDay.text = format.format(date)
        format = SimpleDateFormat("d")
        tvCalendarDate.text = format.format(date)
    }
}
