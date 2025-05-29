package com.example.eventposter.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.eventposter.app.displayDate
import com.example.eventposter.databinding.FragmentEventFilterBinding
import com.example.eventposter.domain.model.FilterEventModel
import java.util.Calendar

class FilterEventFragment: FilterFragment() {

    private var _binding: FragmentEventFilterBinding? = null
    private val binding get() = _binding!!

    private var currentFilter = FilterEventModel()
    override var onFilterApplied: ((FilterModel) -> Unit)? = null

    companion object {
        private var fragment: FilterEventFragment? = null
        private const val DATE_PICKER = "DATE_PICKER"
        private const val TIME_PICKER = "TIME_PICKER"
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDates()

        binding.btnResetEventFilter.setOnClickListener {
            resetFilter()
        }

        binding.btnApplyEventFilter.setOnClickListener {
            if (!(currentFilter.endDate?.after(currentFilter.startDate) == true
                        || currentFilter.endDate == null)) {
                Toast.makeText(
                    context,
                    "Конец диапазона раньше начала.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            onFilterApplied?.invoke(currentFilter)
            dismiss()
        }

        binding.btnStartDatePickerFilter.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->
                currentFilter.startDate = date
                updateDates()
            }
        }

        binding.btnEndDatePickerFilter.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->



                currentFilter.endDate = date
                updateDates()
            }
        }
    }

    private fun resetFilter() {
        currentFilter.startDate = Calendar.getInstance().time
        currentFilter.endDate = null
        updateDates()
    }

    private fun updateDates() {
        binding.tvStartDateEventFilter.displayDate(currentFilter.startDate)

        if (currentFilter.endDate != null) {
            binding.tvEndDateEventFilter.displayDate(currentFilter.endDate!!)
        } else {
            binding.tvEndDateEventFilter.text = "Не выбрано"
        }
    }

    override fun loadFilterSettings(settings: FilterModel) {
        currentFilter = settings as FilterEventModel
    }
}