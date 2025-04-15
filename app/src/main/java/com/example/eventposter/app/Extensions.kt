package com.example.eventposter.app

import android.text.Editable
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toEditable() : Editable =
        Editable.Factory.getInstance().newEditable(this)

fun TextView.displayDate(date: Date) {
        val fmt = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
        text = fmt.format(date)
}