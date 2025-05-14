package com.example.eventposter.app.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.getFormattedDate
import com.example.eventposter.domain.model.FeedbackModel

class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvUserName: TextView = itemView.findViewById(R.id.tvPreviewFeedbackUserName)
    private val tvDate: TextView = itemView.findViewById(R.id.tvPreviewFeedbackDate)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.rbPreviewFeedback)
    private val tvReviewText: TextView = itemView.findViewById(R.id.tvPreviewFeedbackUserText)

    fun bind(feedback: FeedbackModel) {
        tvUserName.text =  if (feedback.isAnonymous) "Аноним" else feedback.userName
        tvDate.text = feedback.date.getFormattedDate()
        ratingBar.rating = feedback.rating
        tvReviewText.text = feedback.text
    }
}