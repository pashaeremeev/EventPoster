package com.example.eventposter.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.displayDate
import com.example.eventposter.app.toEditable
import com.example.eventposter.app.ui.viewmodel.FormEventViewModel
import com.example.eventposter.databinding.FragmentFormEventBinding
import com.example.eventposter.domain.model.EventModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormEventFragment : Fragment() {

    private var eventId: Int? = null
    private lateinit var vm: FormEventViewModel

    private var startDate: Date? = null
    private var endDate: Date? = null

    private var _binding: FragmentFormEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[FormEventViewModel::class.java]

        _binding = FragmentFormEventBinding.inflate(inflater, container, false)

        eventId = arguments?.getInt(EVENT_ID)
        eventId?.let { vm.setEventId(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.event.collect { event ->
                    binding.etFormName.text = (event?.name ?: "").toEditable()
                    binding.etFormAddress.text = (event?.address ?: "").toEditable()

                    event?.startDate?.let { date ->
                        binding.tvStartDateEventForm.displayDate(date = date)
                    }
                    event?.endDate?.let { date ->
                        binding.tvEndDateEventForm.displayDate(date = date)
                    }

                    binding.etFormPrice.text = DecimalFormat(
                        "#0.00",
                        DecimalFormatSymbols(Locale.US)
                    ).format(event?.price ?: 0.00).toEditable()

                    event?.keywords?.forEach { word ->
                        val chip = Chip(context).apply {
                            text = word
                            isCloseIconVisible = true
                            setOnCloseIconClickListener {
                                binding.fbFormKeywords.removeView(this)
                            }
                        }
                        binding.fbFormKeywords.addView(chip)
                    }
                }
            }
        }

        binding.btnStartDatePickerForm.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->
                startDate = date
                binding.tvStartDateEventForm.displayDate(date)
            }
        }

        binding.btnEndDatePickerForm.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(parentFragmentManager, DATE_PICKER)
            datePicker.setListener { date ->
                endDate = date
                binding.tvEndDateEventForm.displayDate(date)
            }
        }

        binding.btnSaveFormEvent.setOnClickListener {
            // Сбор основных данных
            val name = binding.etFormName.text.toString().trim()
            val address = binding.etFormAddress.text.toString().trim()
            val priceText = binding.etFormPrice.text.toString()
            val startDateFromForm = startDate
            val endDateFromForm = endDate

            // Сбор ключевых слов
            val keywords = mutableListOf<String>().apply {
                for (i in 0 until binding.fbFormKeywords.childCount) {
                    (binding.fbFormKeywords.getChildAt(i) as? Chip)?.text?.toString()?.let { keyword ->
                        add(keyword)
                    }
                }
            }

            // Валидация обязательных полей
            when {
                name.isEmpty() -> {
                    binding.etFormName.error = "Введите название события"
                    return@setOnClickListener
                }
                address.isEmpty() -> {
                    binding.etFormAddress.error = "Введите адрес"
                    return@setOnClickListener
                }
                startDateFromForm == null -> {
                    // Показать ошибку для даты начала
                    binding.tvStartDateEventForm.error = "Отсутствует дата"
                    return@setOnClickListener
                }
                startDateFromForm.after(Calendar.getInstance().time) -> {
                    // Показать ошибку для даты начала
                    binding.tvStartDateEventForm.error = "Неактуальная дата"
                    return@setOnClickListener
                }
                startDateFromForm.after(endDateFromForm) && endDateFromForm != null -> {
                    // Показать ошибку для даты начала
                    binding.tvStartDateEventForm.error = "Дата конца раньше даты начала"
                    return@setOnClickListener
                }
            }

            val price = try {
                priceText.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            } catch (e: Exception) {
                binding.etFormPrice.error = "Некорректная сумма"
                return@setOnClickListener
            }

            val event = EventModel(
                id = eventId ?: 0,
                name = name,
                address = address,
                startDate = startDateFromForm ?: Date(),
                endDate = endDateFromForm,
                posterUrl = null,
                price = price,
                keywords = keywords
            )

            vm.saveEvent(event)
        }

        binding.ivBackUserEvents.setOnClickListener {
            toLastFragment()
        }

    }

    companion object {
        fun newInstance(eventId: Int? = null) =
            FormEventFragment().apply {
                arguments = Bundle().apply {
                    eventId?.let { putInt(EVENT_ID, it) }
                }
            }

        private const val DATE_PICKER = "DATE_PICKER"
        private const val EVENT_ID = "EVENT_ID"
    }

    private fun toLastFragment() {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack()
    }

}