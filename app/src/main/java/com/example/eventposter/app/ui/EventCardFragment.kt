package com.example.eventposter.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.ui.adapters.recycler.ImageSliderAdapter
import com.example.eventposter.databinding.FragmentEventCardBinding
import com.example.eventposter.domain.model.EventModel

class EventCardFragment : Fragment() {

    private var _binding: FragmentEventCardBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val EVENT_CARD = "EVENT_CARD"
        fun newInstance() = EventCardFragment()
    }

    private lateinit var vm: EventCardViewModel
    private var event: EventModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventCardBinding.inflate(inflater, container, false)
        event = requireArguments().getParcelable(EVENT_CARD) as EventModel?

        binding.tvEventName.text = event?.name
        binding.tvEventLocation.text = event?.address

        binding.ivBackEventCard.setOnClickListener {
            removeFragment()
        }

        val adapter = ImageSliderAdapter(
            context,
            imageList =
        )
        binding.vpEventImages.adapter = adapter

        return binding.root
    }

    private fun removeFragment() {
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .popBackStack()
    }

}