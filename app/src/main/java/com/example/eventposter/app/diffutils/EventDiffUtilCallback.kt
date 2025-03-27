package com.example.eventposter.app.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.eventposter.domain.model.EventModel

class EventDiffUtilCallback(
    private val oldEvents: List<EventModel>,
    private val newEvents: List<EventModel>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldEvents.size

    override fun getNewListSize(): Int = newEvents.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldEvents[oldItemPosition]
        val new = newEvents[newItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldEvents[oldItemPosition]
        val new = newEvents[newItemPosition]
        return old.startDate == new.startDate
                && old.endDate == new.endDate
                && old.address == new.name
                && old.posterUrl == new.posterUrl
    }
}