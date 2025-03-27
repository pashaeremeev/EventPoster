package com.example.eventposter.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.CalendarClickListener
import com.example.eventposter.app.EventClickListener
import com.example.eventposter.app.ui.DatePickerFragment
import com.example.eventposter.app.ui.adapters.recycler.CalendarAdapter
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.databinding.FragmentHomeBinding
import com.example.eventposter.domain.model.EventModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var fragment: HomeFragment? = null
        private const val DATE_PICKER = "DATE_PICKER"
        private const val EVENT_CARD = "EVENT_CARD"
        fun getInstance(): HomeFragment {
            if (fragment == null) {
                fragment = HomeFragment()
            }
            return fragment!!
        }
        const val DAY_IN_MILLIS: Long = 86_400_000
    }

    private lateinit var vm: HomeViewModel
    private lateinit var adapterCalendar: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapterCalendar = CalendarAdapter(requireContext(), object: CalendarClickListener {
            override fun invoke(date: Date) {
                vm.setSelectedDate(date)
            }
        })

        binding.rvCalendarDays.adapter = adapterCalendar
        binding.rvCalendarDays.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateMonthYear()
            }
        })

        vm.selectedDate.observe(viewLifecycleOwner) { date ->
            adapterCalendar.setSelected(date)
            displayDate(date)
        }

        displayDate(Calendar.getInstance().time)

        vm.days.observe(viewLifecycleOwner) { days ->
            adapterCalendar.days = days
            vm.selectedDate.value?.let { adapterCalendar.setSelected(it) }
        }

        val adapterPreviews = EventAdapter(requireContext(), object : EventClickListener {
            override fun invoke(event: EventModel?) {
                event?.let { clickOnEventView(it) }
            }
        })
        val cal = Calendar.getInstance()
        vm.setSelectedDate(cal.time)
        val events = listOf(
                EventModel(
                    id = 1,
                    name = "Новый год на Красной Площади",
                    address = "г. Чебоксары, Красная Площадь",
                    startDate =  cal.time,
                    endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
                    posterUrl = "https://fs01.cap.ru/www22-09/gcheb/news/2023/01/18/9d4048df-fdf8-4300-a022-336e28ccc8f1/zaliv.jpg"
                ),
                EventModel(
                    id = 2,
                    name = "Раздача алмазов",
                    address = "г. Москва, Красная Площадь",
                    startDate =  cal.time,
                    endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 3),
                    posterUrl = "https://kultura.orb.ru/uploads/images/afisha/2023/12/afisha_13020_0.jpg"
                ),
                EventModel(
                    id = 3,
                    name = "Путешествие в Америку",
                    address = "г. Архангельск, Порт",
                    startDate =  cal.time,
                    endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 120),
                    posterUrl = "https://m.media-amazon.com/images/M/MV5BMjA0ZDlkNzMtYjVlNS00MWY2LWE3N2ItMDZlMDEwNWU2N2M5XkEyXkFqcGdeQXVyMzY0MTE3NzU@._V1_.jpg"
                ),
                EventModel(
                    id = 4,
                    name = "Приглашение в гости",
                    address = "г. Чебоксары, пр. Мира, д. 48",
                    startDate =  cal.time,
                    endDate = Date(cal.timeInMillis + DAY_IN_MILLIS * 5),
                    posterUrl = null
                )
        )

        vm.setEvents(events)

        binding.rvEventPreviews.adapter = adapterPreviews

        vm.events.observe(viewLifecycleOwner) { newEvents ->
            val result = adapterPreviews.setEvents(newEvents)
            result.dispatchUpdatesTo(adapterPreviews)
        }

        binding.btnDatePicker.setOnClickListener {
            DatePickerFragment().apply {
                setListener { date ->
                    vm.setSelectedDate(date)
                }
            }.show(parentFragmentManager, DATE_PICKER)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMonthYear() {
        val lm = binding.rvCalendarDays.layoutManager as LinearLayoutManager
        val currentPos = lm.findFirstVisibleItemPosition()
        if (currentPos != RecyclerView.NO_POSITION) {
            val visibleDay = (binding.rvCalendarDays.adapter as CalendarAdapter).days[currentPos]
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

    private fun displayDate(date: Date) {
        val fmt = SimpleDateFormat("d MMMM yyyy", Locale("ru", "RU"))
        binding.tvDate.text = fmt.format(date)
    }

    private fun clickOnEventView(event: EventModel) {
        val bundle = Bundle().apply {
            putParcelable(EVENT_CARD, event)
        }
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .navigate(R.id.action_navigation_home_to_navigation_event_card, bundle)
    }

}