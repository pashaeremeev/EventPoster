package com.example.eventposter.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.eventposter.databinding.FragmentFormFeedbackBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FormFeedbackFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFormFeedbackBinding? = null
    private val binding get() = _binding!!
    private var eventId: Int = 0
    private val userId = 1
    private lateinit var vm: FormFeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getInt(EVENT_ID, 0)
        }
        vm = ViewModelProvider(this)[FormFeedbackViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.tvRatingValue.text = rating.toString()
        }
    }

    private fun setupListeners() {
        binding.btnSaveFeedback.setOnClickListener {
            if (validateForm()) {
                vm.createFeedback(
                    userId = userId,
                    binding.cbAnonymousFeedback.isChecked,
                    binding.ratingBar.rating,
                    binding.etFeedbackText.text.toString(),
                    eventId = eventId
                )
                dismiss()
            }
        }

        binding.btnCancelFeedback.setOnClickListener {
            dismiss()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (binding.ratingBar.rating == 0f) {
            binding.tvRatingError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvRatingError.visibility = View.GONE
        }

        if (binding.etFeedbackText.text.toString().trim().isEmpty()) {
            binding.etFeedbackText.error = "Поле текста пустое"
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EVENT_ID = "EVENT_ID"

        fun newInstance(eventId: Int): FormFeedbackFragment {
            return FormFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putInt(EVENT_ID, eventId)
                }
            }
        }
    }
}