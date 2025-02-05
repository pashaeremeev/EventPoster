package com.example.eventposter.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.app.CalendarClickListener
import com.example.eventposter.app.ui.adapters.recycler.CalendarAdapter
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.databinding.FragmentHomeBinding
import com.example.eventposter.domain.EventModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        private var fragment: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (fragment == null) {
                fragment = HomeFragment()
            }
            return fragment!!
        }
    }

    private lateinit var rvEventsPreviews: RecyclerView
    private lateinit var  rvCalendarDays: RecyclerView
    private lateinit var adapterCalendar: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvEventsPreviews = binding.rvEventPreviews
        rvCalendarDays = binding.rvCalendarDays

        adapterCalendar = CalendarAdapter(requireContext(), object: CalendarClickListener {
            override fun invoke(date: Date) {
                displayDate(date)
                adapterCalendar.setSelected(date)
            }
        })
        val calendar = Calendar.getInstance()
        displayDate(calendar.time)
        val lengthOfDays = 90
        val dates = mutableListOf<Date>()
        for (i in 0 until lengthOfDays) {
            dates.add(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }
        adapterCalendar.days = dates
        rvCalendarDays.adapter = adapterCalendar
        rvCalendarDays.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateMonthYear()
            }
        })

        val adapterPreviews = EventAdapter(requireContext())
        adapterPreviews.events.add(
            EventModel(
            "Новый год на Красной Площади",
            "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                "https://fs01.cap.ru/www22-09/gcheb/news/2023/01/18/9d4048df-fdf8-4300-a022-336e28ccc8f1/zaliv.jpg"
            )
        )
        adapterPreviews.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )
        adapterPreviews.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )
        adapterPreviews.events.add(
            EventModel(
                "Новый год на Красной Площади",
                "г. Чебоксары, Красная Площадь",
                Calendar.getInstance().time,
                Calendar.getInstance().time,
                null
            )
        )
        rvEventsPreviews.adapter = adapterPreviews

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateMonthYear() {
        val lm = rvCalendarDays.layoutManager as LinearLayoutManager
        val currentPos = lm.findFirstVisibleItemPosition()
        if (currentPos != RecyclerView.NO_POSITION) {
            val visibleDay = (rvCalendarDays.adapter as CalendarAdapter).days[currentPos]
            val cal = Calendar.getInstance()
            cal.time = visibleDay
            val fmtMonthYear = SimpleDateFormat("LLLL yyyy", Locale("ru"))
            var strMonthYear = fmtMonthYear.format(cal.time)
            strMonthYear = strMonthYear.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale("ru"))
                else it.toString()
            }
            binding.tvMonthYear.text = strMonthYear
        }
    }

    fun displayDate(date: Date) {
        val fmt = SimpleDateFormat("d MMMM", Locale("ru", "RU"))
        binding.tvDate.text = fmt.format(date)
    }

}