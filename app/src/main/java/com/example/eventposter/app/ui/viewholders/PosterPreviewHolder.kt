package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R

class PosterPreviewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var posterPreviewImage: ImageView
    private val posterPreviewEventNameText: TextView
    private val posterPreviewIvLocation: ImageView
    private val posterPreviewEventLocationText: TextView
    private val posterPreviewIvDateTime: ImageView
    private val posterPreviewEventDateTimeText: TextView

    init {
        posterPreviewImage = itemView.findViewById(R.id.poster_preview_image)
        posterPreviewEventNameText = itemView.findViewById(R.id.poster_preview_event_name_text)
        posterPreviewIvLocation = itemView.findViewById(R.id.poster_preview_iv_location)
        posterPreviewEventLocationText = itemView.findViewById(R.id.poster_preview_event_location_text)
        posterPreviewIvDateTime = itemView.findViewById(R.id.poster_preview_iv_datetime)
        posterPreviewEventDateTimeText = itemView.findViewById(R.id.poster_preview_event_datetime_text)
    }

    fun getPosterPreviewImage(): ImageView {
        return posterPreviewImage
    }

    fun getPosterPreviewEventNameText(): TextView {
        return posterPreviewEventNameText
    }

    fun getPosterPreviewIvLocation(): ImageView {
        return posterPreviewIvLocation
    }

    fun getPosterPreviewEventLocationText(): TextView {
        return posterPreviewEventLocationText
    }

    fun getPosterPreviewIvDateTime(): ImageView {
        return posterPreviewIvDateTime
    }

    fun getPosterPreviewEventDateTimeText(): TextView {
        return posterPreviewEventDateTimeText
    }
}