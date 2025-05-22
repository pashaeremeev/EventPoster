package com.example.eventposter.app.ui.adapters.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.TicketClickListener
import com.example.eventposter.app.diffutils.UserDiffUtilCallback
import com.example.eventposter.app.ui.viewholder.TicketViewHolder
import com.example.eventposter.domain.model.UserModel

class TicketAdapter(
    private val context: Context,
    private val clickListener: TicketClickListener
): RecyclerView.Adapter<TicketViewHolder?>() {

    private var friends = listOf<UserModel>()

    fun setUsers(users: List<UserModel>): DiffUtil.DiffResult {
        val diff = UserDiffUtilCallback(
            oldUsers = friends,
            newUsers = users
        )
        this.friends = users
        return DiffUtil.calculateDiff(diff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_ticket_search, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val friend = friends[position]
        holder.getUserNameText().text = friend.name
        holder.itemView.setOnClickListener { clickListener.invoke() }
        //holder.getEventNameText().text = ticket.event.name
    }

}