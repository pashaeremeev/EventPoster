package com.example.eventposter.app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholders.PosterPreviewHolder
import com.example.eventposter.domain.EventModel

class PosterPreviewAdapter(
    context: Context
): RecyclerView.Adapter<PosterPreviewHolder?>() {

    var events: ArrayList<EventModel> = arrayListOf()
        set(value) {
            field = value
        }
    private val context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterPreviewHolder {
        return PosterPreviewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_poster_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: PosterPreviewHolder, position: Int) {
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