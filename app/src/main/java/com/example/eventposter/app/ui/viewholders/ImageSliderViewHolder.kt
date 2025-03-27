package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R

class ImageSliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imageSliderViewHolder: ImageView

    init {
        imageSliderViewHolder = itemView.findViewById(R.id.ivSliderItem)
    }
}