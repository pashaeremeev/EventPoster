package com.example.eventposter.app.ui.event.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.example.eventposter.app.Searchable
import com.example.eventposter.app.ui.FilterSettingsModel
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.databinding.FragmentEventsSearchBinding
import com.example.eventposter.domain.EventModel
import com.example.eventposter.domain.FilterSettingsEventModel
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
    }

    private lateinit var vm: EventsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[EventsSearchViewModel::class.java]
        _binding = FragmentEventsSearchBinding.inflate(inflater, container, false)

        val adapter = EventAdapter(requireContext())

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

    override fun onFilterChanged(newSettings: FilterSettingsModel) {
        if (::vm.isInitialized && newSettings is FilterSettingsEventModel) {
            vm.updateFilter { newSettings.copy() }
        }
    }

    override fun onFilterChanged(newText: String) {
        if (::vm.isInitialized) {
            vm.updateFilter { copy(text = newText) }
        }
    }

}