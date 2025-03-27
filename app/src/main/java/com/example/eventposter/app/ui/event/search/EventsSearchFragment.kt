package com.example.eventposter.app.ui.event.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import java.util.Calendar
import java.util.Date


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
        const val DAY_IN_MILLIS: Long = 86_400_000
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

        val cal = Calendar.getInstance()
        val events = listOf(
            EventModel(
                id = 1,
                name = "Новый год на Красной Площади",
                address = "г. Чебоксары, Красная Площадь",
                startDate =  Date(cal.timeInMillis - DAY_IN_MILLIS * 2),
                endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
                posterUrl = "https://fs01.cap.ru/www22-09/gcheb/news/2023/01/18/9d4048df-fdf8-4300-a022-336e28ccc8f1/zaliv.jpg"
            ),
            EventModel(
                id = 2,
                name = "Раздача алмазов",
                address = "г. Москва, Красная Площадь",
                startDate =  Date(cal.timeInMillis - DAY_IN_MILLIS * 1),
                endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 3),
                posterUrl = "https://kultura.orb.ru/uploads/images/afisha/2023/12/afisha_13020_0.jpg"
            ),
            EventModel(
                id = 3,
                name = "Путешествие в Америку",
                address = "г. Архангельск, Порт",
                startDate =  Date(cal.timeInMillis - DAY_IN_MILLIS * 1),
                endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 120),
                posterUrl = "https://m.media-amazon.com/images/M/MV5BMjA0ZDlkNzMtYjVlNS00MWY2LWE3N2ItMDZlMDEwNWU2N2M5XkEyXkFqcGdeQXVyMzY0MTE3NzU@._V1_.jpg"
            ),
            EventModel(
                id = 4,
                name = "Приглашение в гости",
                address = "г. Чебоксары, пр. Мира, д. 48",
                startDate =  Date(cal.timeInMillis - (DAY_IN_MILLIS / 2)),
                endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
                posterUrl = null
            )
        )

        vm.setEvents(events)

        vm.events.observe(viewLifecycleOwner) { newEvents ->
            val diff: DiffUtil.DiffResult = adapter.setEvents(newEvents)
            diff.dispatchUpdatesTo(adapter)
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
            putParcelable(EVENT_CARD, event)
        }
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .navigate(R.id.action_navigation_search_to_navigation_event_card, bundle)
    }

}