package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.diffutils.EventDiffUtilCallback
import com.example.eventposter.app.ui.viewholders.EventHolder
import com.example.eventposter.domain.EventModel

class EventAdapter(
    private val context: Context
): RecyclerView.Adapter<EventHolder?>() {

    private var events: List<EventModel> = listOf()

    fun setEvents(newEvents: List<EventModel>): DiffUtil.DiffResult {
        val diff = EventDiffUtilCallback(events, newEvents)
        this.events = newEvents
        return DiffUtil.calculateDiff(diff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_event_preview, parent, false)
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        Glide.with(context)
            .load(event.posterUrl)
            .error(R.drawable.ic_image_not_supported_24dp)
            .into(holder.getPosterPreviewImage())
    }


}