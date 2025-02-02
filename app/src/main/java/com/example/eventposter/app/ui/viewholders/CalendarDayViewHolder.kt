package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarDayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var tvCalendarDay = itemView.findViewById<TextView>(R.id.tvCalendarDay)
    private var tvCalendarDate = itemView.findViewById<TextView>(R.id.tvCalendarDate)

    fun bind(date: Date) {
        var format = SimpleDateFormat("EE", Locale("ru", "RU"))
        tvCalendarDay.text = format.format(date)
        format = SimpleDateFormat("d")
        tvCalendarDate.text = format.format(date)
    }
}