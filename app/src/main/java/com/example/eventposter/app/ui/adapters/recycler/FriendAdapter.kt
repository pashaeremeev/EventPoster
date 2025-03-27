package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.diffutils.UserDiffUtilCallback
import com.example.eventposter.app.ui.viewholders.FriendHolder
import com.example.eventposter.domain.model.UserModel

class FriendAdapter(
    private val context: Context
): RecyclerView.Adapter<FriendHolder?>() {

    private var friends = listOf<UserModel>()

    fun setUsers(users: List<UserModel>): DiffUtil.DiffResult {
        val diff = UserDiffUtilCallback(
            oldUsers = friends,
            newUsers = users
        )
        this.friends = users
        return DiffUtil.calculateDiff(diff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        return FriendHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_user_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        val friend = friends[position]
        holder.getUserNameText().text = friend.name
        Glide.with(context)
            .load(friend.urlIcon)
            .error(R.drawable.ic_image_not_supported_24dp)
            .into(holder.getProfileIcon())
    }

}