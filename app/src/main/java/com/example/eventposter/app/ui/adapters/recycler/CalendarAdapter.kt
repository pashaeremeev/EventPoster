package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholders.CalendarCurDayViewHolder
import com.example.eventposter.app.ui.viewholders.CalendarDayViewHolder
import java.util.Date

class CalendarAdapter(
    private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder?>()  {

    var days = listOf<Date>()

    companion object {
        private const val TYPE_CURRENT_DAY = 0
        private const val TYPE_NORMAL_DAY = 1
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) TYPE_CURRENT_DAY else TYPE_NORMAL_DAY


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CURRENT_DAY) {
            CalendarCurDayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar_cur_day, parent, false))
        } else {
            CalendarDayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false))
        }
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val day = days[position]
        if (holder is CalendarCurDayViewHolder) {
            holder.bind(day)
        } else if (holder is CalendarDayViewHolder) {
            holder.bind(day)
        }
    }
}