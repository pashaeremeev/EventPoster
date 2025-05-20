package com.example.eventposter.app.ui.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholder.FeedbackViewHolder
import com.example.eventposter.domain.model.FeedbackModel

class FeedbackAdapter(
    private var feedbacks: List<FeedbackModel>
) : RecyclerView.Adapter<FeedbackViewHolder>() {

    private val maxSizeOfFeedbacksOnCard = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_feedback, parent, false)
        return FeedbackViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbacks[position])
    }

    override fun getItemCount() = feedbacks.size.coerceAtMost(maxSizeOfFeedbacksOnCard)

    fun setFeedbacks(newFeedbacks: List<FeedbackModel>) {
        feedbacks = newFeedbacks
    }
}