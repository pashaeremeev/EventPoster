package com.example.eventposter.app.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R

class TicketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val userNameText: TextView
    private val eventNameText: TextView

    init {
        userNameText = itemView.findViewById(R.id.tvTicketPreviewFullName)
        eventNameText = itemView.findViewById(R.id.tvTicketPreviewEventName)
    }

    fun getEventNameText() = eventNameText

    fun getUserNameText() = userNameText

}