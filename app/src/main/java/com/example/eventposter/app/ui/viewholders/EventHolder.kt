package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.domain.model.EventModel
import java.text.SimpleDateFormat
import java.util.Locale

class EventHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var posterPreviewImage: ImageView
    private val posterPreviewEventNameText: TextView
    private val posterPreviewIvLocation: ImageView
    private val posterPreviewEventLocationText: TextView
    private val posterPreviewIvDateTime: ImageView
    private val posterPreviewEventDateTimeText: TextView

    init {
        posterPreviewImage = itemView.findViewById(R.id.poster_preview_image)
        posterPreviewEventNameText = itemView.findViewById(R.id.tvPosterPreviewEventName)
        posterPreviewIvLocation = itemView.findViewById(R.id.ivPosterPreviewLocation)
        posterPreviewEventLocationText = itemView.findViewById(R.id.tvPosterPreviewEventLocation)
        posterPreviewIvDateTime = itemView.findViewById(R.id.ivPosterPreviewDateTime)
        posterPreviewEventDateTimeText = itemView.findViewById(R.id.tvPosterPreviewDateTime)
    }

    fun getPosterPreviewImage() = posterPreviewImage

    fun bind(event: EventModel) {
        posterPreviewEventNameText.text = event.name
        posterPreviewEventLocationText.text = event.address
        var fmt = SimpleDateFormat("d MMMM HH:mm", Locale("ru", "RU"))
        val startAndEnd =
            if (event.endDate != null)
                fmt.format(event.startDate) + " – " + fmt.format(event.endDate)
            else fmt.format(event.startDate)
        posterPreviewEventDateTimeText.text = startAndEnd
    }
}