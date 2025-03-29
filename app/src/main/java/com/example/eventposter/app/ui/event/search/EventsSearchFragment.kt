package com.example.eventposter.app.ui.event.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.example.eventposter.R
import com.example.eventposter.app.EventClickListener
import com.example.eventposter.app.Searchable
import com.example.eventposter.app.ui.FilterModel
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.databinding.FragmentEventsSearchBinding
import com.example.eventposter.domain.model.EventModel
import com.example.eventposter.domain.model.FilterEventModel
import kotlinx.coroutines.launch


class EventsSearchFragment : Fragment(), Searchable {

    private var _binding: FragmentEventsSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var fragment: EventsSearchFragment? = null
        fun getInstance(): EventsSearchFragment {
            if (fragment == null) {
                fragment = EventsSearchFragment()
            }
            return fragment!!
        }
        private const val EVENT_CARD = "EVENT_CARD"
    }

    private lateinit var vm: EventsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[EventsSearchViewModel::class.java]
        _binding = FragmentEventsSearchBinding.inflate(inflater, container, false)

        val adapter = EventAdapter(requireContext(), object : EventClickListener {
                override fun invoke(event: EventModel?) {
                    event?.let { clickOnEventView(it) }
                }
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.events.collect { events ->
                    val diff: DiffUtil.DiffResult = adapter.setEvents(events)
                    diff.dispatchUpdatesTo(adapter)
                }
            }
        }

        binding.rvEventSearchResult.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFilterChanged(newSettings: FilterModel) {
        if (::vm.isInitialized && newSettings is FilterEventModel) {
            vm.updateFilter { newSettings.copy() }
        }
    }

    override fun onFilterChanged(newText: String) {
        if (::vm.isInitialized) {
            vm.updateFilter { copy(text = newText) }
        }
    }

    private fun clickOnEventView(event: EventModel) {
        val bundle = Bundle().apply {
            putInt(EVENT_CARD, event.id)
        }
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .navigate(R.id.action_navigation_search_to_navigation_event_card, bundle)
    }

}