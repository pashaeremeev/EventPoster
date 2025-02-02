package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholders.EventHolder
import com.example.eventposter.domain.EventModel

class EventAdapter(
    private val context: Context
): RecyclerView.Adapter<EventHolder?>() {

    var events: ArrayList<EventModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_event_preview, parent, false)
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.getPosterPreviewEventNameText().text = event.name
        holder.getPosterPreviewEventLocationText().text = event.address
        holder.getPosterPreviewEventDateTimeText().text =
            event.startDate.toString() + "\n" + event.endDate.toString()
        Glide.with(context)
            .load(event.posterUrl)
            .error(R.drawable.ic_image_not_supported_24dp)
            .into(holder.getPosterPreviewImage())
    }


}