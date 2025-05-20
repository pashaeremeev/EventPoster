package com.example.eventposter.app.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R

class FriendHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val profileIcon: ImageView
    private val userNameText: TextView

    init {
        profileIcon = itemView.findViewById(R.id.ivProfileIcon)
        userNameText = itemView.findViewById(R.id.tvUserNameText)
    }

    fun getProfileIcon() = profileIcon

    fun getUserNameText() = userNameText

}