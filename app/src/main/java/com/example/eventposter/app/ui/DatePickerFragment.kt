package com.example.eventposter.app.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.eventposter.R
import java.util.Calendar
import java.util.Date

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: ((Date) -> Unit)? = null

    fun setListener(listener: (Date) -> Unit) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val minDate = Calendar.getInstance().apply {
            set(currentYear, currentMonth, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
        val dialog = DatePickerDialog(
            requireActivity(),
            R.style.DatePicker,
            this,
            currentYear,
            currentMonth,
            currentDay
        )
        dialog.datePicker.minDate = minDate
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener?.invoke(Calendar.getInstance().apply {
            set(year, month, day)
        }.time)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener = null
    }
}