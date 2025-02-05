package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.CalendarClickListener
import com.example.eventposter.app.ui.viewholders.CalendarDayViewHolder
import java.text.SimpleDateFormat
import java.util.Date

class CalendarAdapter(
    val context: Context,
    private val clickListener: CalendarClickListener
): RecyclerView.Adapter<CalendarDayViewHolder?>()  {

    var days = listOf<Date>()
    private var selectedPosition: Int? = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayViewHolder {
        return CalendarDayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false))
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: CalendarDayViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)
        holder.getCvCardCalendar().strokeColor = context.getColor(R.color.red)
        if (position == selectedPosition) {
            holder.getCvCardCalendar().strokeWidth = 4
        } else {
            holder.getCvCardCalendar().strokeWidth = 0
        }
        holder.itemView.setOnClickListener { clickListener.invoke(day) }
    }

    fun setSelected(date: Date) {
        val fmt = SimpleDateFormat("yyyyMMdd")
        val position = days.indexOfFirst { day -> fmt.format(day) == fmt.format(date) }
        val oldSelectedPosition = selectedPosition
        selectedPosition = position
        if (oldSelectedPosition != null) {
            notifyItemChanged(oldSelectedPosition)
        }
        notifyItemChanged(position)
    }
}