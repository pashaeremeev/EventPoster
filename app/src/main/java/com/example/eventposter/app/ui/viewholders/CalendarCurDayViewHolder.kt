package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarCurDayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var tvCalendarCurDay = itemView.findViewById<TextView>(R.id.tvCalendarCurDay)
    private var tvCalendarCurDate = itemView.findViewById<TextView>(R.id.tvCalendarCurDate)

    fun bind(date: Date) {
        var format = SimpleDateFormat("EE", Locale("ru", "RU"))
        tvCalendarCurDay.text = format.format(date)
        format = SimpleDateFormat("d")
        tvCalendarCurDate.text = format.format(date)
    }
}