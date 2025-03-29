package com.example.eventposter.app.ui

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
import com.example.eventposter.databinding.FragmentEventCardBinding
import kotlinx.coroutines.launch

class EventCardFragment : Fragment() {

    private var _binding: FragmentEventCardBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val EVENT_CARD = "EVENT_CARD"
        fun newInstance() = EventCardFragment()
    }

    private lateinit var vm: EventCardViewModel
    private var idEvent: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[EventCardViewModel::class.java]

        _binding = FragmentEventCardBinding.inflate(inflater, container, false)

        idEvent = requireArguments().getInt(EVENT_CARD)
        idEvent?.let { vm.setEventId(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.event.collect { event ->
                    binding.tvEventName.text = event?.name
                    binding.tvEventLocation.text = event?.address
                }
            }
        }

        binding.ivBackEventCard.setOnClickListener {
            removeFragment()
        }

//        val adapter = ImageSliderAdapter(
//            context,
//            imageList =
//        )
//        binding.vpEventImages.adapter = adapter
    }

    private fun removeFragment() {
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .popBackStack()
    }

}