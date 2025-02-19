package com.example.eventposter.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventposter.databinding.FragmentUserFilterBinding
import com.example.eventposter.domain.FilterSettingsUserModel

class FilterUserFragment: FilterFragment() {

    private var _binding: FragmentUserFilterBinding? = null
    private val binding get() = _binding!!

    private var currentFilter = FilterSettingsUserModel()
    override var onFilterApplied: ((FilterSettingsModel) -> Unit)? = null

    companion object {
        private var fragment: FilterEventFragment? = null
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

        _binding = FragmentUserFilterBinding.inflate(inflater, container, false)

        currentFilter.age?.let {
            binding.etUserAge.setText(it.toString())
        }

        binding.btnResetUserFilter.setOnClickListener {
            resetFilter()
        }

        binding.btnApplyUserFilter.setOnClickListener {
            onFilterApplied?.invoke(currentFilter)
            dismiss()
        }

        binding.etUserAge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentFilter.age = s.toString().toIntOrNull()
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        return binding.root
    }

    private fun resetFilter() {
        currentFilter.age = null
        binding.etUserAge.setText("")
    }

    override fun loadFilterSettings(settings: FilterSettingsModel) {
        currentFilter = settings as FilterSettingsUserModel
    }
}