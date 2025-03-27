package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholders.ImageSliderViewHolder

class ImageSliderAdapter(
    private val context: Context,
    private val imageList: List<String>
) : RecyclerView.Adapter<ImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = imageList[position]
        Glide.with(context)
            .load(imageUrl)
            .error(R.drawable.ic_image_not_supported_24dp)
            .into(holder.imageSliderViewHolder)
    }
}