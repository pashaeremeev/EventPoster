package com.example.eventposter.app.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.eventposter.domain.model.UserModel

class UserDiffUtilCallback(
    private val oldUsers: List<UserModel>,
    private val newUsers: List<UserModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldUsers.size
    }

    override fun getNewListSize(): Int {
        return newUsers.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldUsers[oldItemPosition]
        val new = newUsers[newItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldUsers[oldItemPosition]
        val new = newUsers[newItemPosition]
        return old.name == new.name && old.urlIcon == new.urlIcon
    }
}