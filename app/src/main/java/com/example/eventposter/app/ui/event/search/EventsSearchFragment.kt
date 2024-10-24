package com.example.eventposter.app.ui.event.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.databinding.FragmentEventsSearchBinding
import com.example.eventposter.domain.EventModel
import java.util.Calendar

class EventsSearchFragment : Fragment() {

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
    }

    private lateinit var viewModel: EventsSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[EventsSearchViewModel::class.java]
        _binding = FragmentEventsSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvEventSearchResult
        val adapter = EventAdapter(requireContext())

        adapter.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                "https://fs01.cap.ru/www22-09/gcheb/news/2023/01/18/9d4048df-fdf8-4300-a022-336e28ccc8f1/zaliv.jpg"
            )
        )
        adapter.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )
        adapter.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )
        adapter.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )

        recyclerView.adapter = adapter

        return root
    }

}