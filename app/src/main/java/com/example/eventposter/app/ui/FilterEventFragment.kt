package com.example.eventposter.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eventposter.databinding.FragmentEventFilterBinding
import com.example.eventposter.domain.FilterSettingsEventModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FilterEventFragment: FilterFragment() {

    private var _binding: FragmentEventFilterBinding? = null
    private val binding get() = _binding!!

    private var currentFilter = FilterSettingsEventModel()
    override var onFilterApplied: ((FilterSettingsModel) -> Unit)? = null

    companion object {
        private var fragment: FilterEventFragment? = null
        private const val DATE_PICKER = "DATE_PICKER"
        fun getInstance(): FilterEventFragment {
            if (fragment == null) {
                fragment = FilterEventFragment()
            }
            return fragment!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEventFilterBinding.inflate(inflater, container, false)

        updateDates()

        // Категории
        binding.checkboxConcert.setOnCheckedChangeListener { _, isChecked ->
            currentFilter.categories["concert"] = isChecked
        }

        // Цена
        /*binding.priceSlider.addOnChangeListener { slider, _, _ ->
            currentFilter.minPrice = slider.values[0]
            currentFilter.maxPrice = slider.values[1]
        }*/

        // Сброс
        binding.btnResetEventFilter.setOnClickListener {
            resetFilter()
        }

        // Применить
        binding.btnApplyEventFilter.setOnClickListener {
            onFilterApplied?.invoke(currentFilter)
            dismiss()
        }

        binding.btnStartDatePickerFilter.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->
                currentFilter.startDate = date
                displayDate(
                    view = binding.tvStartDateEventFilter,
                    date = date
                )
            }
        }

        binding.btnEndDatePickerFilter.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->
                currentFilter.endDate = date
                displayDate(
                    view = binding.tvEndDateEventFilter,
                    date = date
                )
            }
        }

        return binding.root
    }

    private fun resetFilter() {
        currentFilter.startDate = Calendar.getInstance().time
        currentFilter.endDate = null
        updateDates()
    }

    private fun displayDate(view: TextView, date: Date) {
        val fmt = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
        view.text = fmt.format(date)
    }

    private fun updateDates() {
        displayDate(
            view = binding.tvStartDateEventFilter,
            date = currentFilter.startDate
        )

        currentFilter.endDate?.let {
            displayDate(
                view = binding.tvEndDateEventFilter,
                date = it
            )
        }
    }

    override fun loadFilterSettings(settings: FilterSettingsModel) {
        currentFilter = settings as FilterSettingsEventModel
    }
}