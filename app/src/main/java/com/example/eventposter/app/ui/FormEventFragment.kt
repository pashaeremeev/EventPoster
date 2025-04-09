package com.example.eventposter.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.eventposter.R
import com.example.eventposter.databinding.FragmentEventCardBinding
import com.example.eventposter.databinding.FragmentFormEventBinding

class FormEventFragment : Fragment() {

    private var eventId: Int? = null

    private var _binding: FragmentFormEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getInt(EVENT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val vm = ViewModelProvider(this)[FormEventViewModel::class.java]

        _binding = FragmentFormEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        fun newInstance(eventId: Int) =
            FormEventFragment().apply {
                arguments = Bundle().apply {
                    putInt(EVENT_ID, eventId)
                }
            }

        private const val EVENT_ID = "EVENT_ID"
    }
}