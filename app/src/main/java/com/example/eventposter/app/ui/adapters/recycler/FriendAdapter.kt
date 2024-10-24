package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewholders.FriendHolder
import com.example.eventposter.domain.UserModel

class FriendAdapter(
    private val context: Context
): RecyclerView.Adapter<FriendHolder?>() {

    var friends = arrayListOf<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        return FriendHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_friend_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        val friend = friends[position]
        holder.getUserNameText().text = friend.userName
        Glide.with(context)
            .load(friend.userIconUrl)
            .error(R.drawable.ic_image_not_supported_24dp)
            .into(holder.getProfileIcon())
    }

}