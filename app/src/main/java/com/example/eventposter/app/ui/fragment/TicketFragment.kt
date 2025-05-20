package com.example.eventposter.app.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventposter.app.ui.viewmodel.TicketViewModel
import com.example.eventposter.databinding.FragmentTicketBinding

class TicketFragment : Fragment() {

    companion object {
        fun newInstance() = TicketFragment()
    }

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: TicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[TicketViewModel::class.java]
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}