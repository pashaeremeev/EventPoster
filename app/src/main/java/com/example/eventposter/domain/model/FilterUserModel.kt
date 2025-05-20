package com.example.eventposter.domain.model

import com.example.eventposter.app.ui.fragment.FilterModel
import com.example.eventposter.data.FilterUser

data class FilterUserModel(
    var name: String = "",
    var age: Int? = null
): FilterModel() {

    fun toData(): FilterUser {
        return FilterUser(
            name = name,
            age = age
        )
    }

}