package com.example.eventposter.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventposter.app.ui.adapters.PosterPreviewAdapter
import com.example.eventposter.databinding.FragmentHomeBinding
import com.example.eventposter.domain.EventModel
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.posterPreviews

        val adapter = PosterPreviewAdapter(root.context)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val HOME_TAG = "HOME_TAG"
        private var fragment: HomeFragment? = null
        fun getInstance(): HomeFragment? {
            if (fragment == null) {
                fragment = HomeFragment()
            }
            return fragment
        }
    }
}