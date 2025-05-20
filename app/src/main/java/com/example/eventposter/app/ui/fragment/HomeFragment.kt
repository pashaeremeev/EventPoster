package com.example.eventposter.app.ui.fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventposter.R
import com.example.eventposter.app.CalendarClickListener
import com.example.eventposter.app.EventClickListener
import com.example.eventposter.app.ui.adapters.recycler.CalendarAdapter
import com.example.eventposter.app.ui.adapters.recycler.EventAdapter
import com.example.eventposter.app.ui.viewmodel.HomeViewModel
import com.example.eventposter.databinding.FragmentHomeBinding
import com.example.eventposter.domain.model.EventModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

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
    }

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var vm: HomeViewModel
    private lateinit var adapterCalendar: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.selectedDate.collect { date ->
                    adapterCalendar.setSelected(date)
                    displayDate(date)
                }
            }
        }

        displayDate(Calendar.getInstance().time)

        vm.days.observe(viewLifecycleOwner) { days ->
            adapterCalendar.days = days
            vm.selectedDate.value.let { adapterCalendar.setSelected(it) }
        }

        val adapterPreviews = EventAdapter(requireContext(), object : EventClickListener {
            override fun invoke(event: EventModel?) {
                event?.let { clickOnEventView(it) }
            }
        })
        val cal = Calendar.getInstance()
        vm.setSelectedDate(cal.time)

        binding.rvEventPreviews.adapter = adapterPreviews

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.events.collect { events ->
                    val result = adapterPreviews.setEvents(events)
                    result.dispatchUpdatesTo(adapterPreviews)
                }
            }
        }

        binding.btnDatePicker.setOnClickListener {
            DatePickerFragment().apply {
                setListener { date ->
                    vm.setSelectedDate(date)
                }
            }.show(parentFragmentManager, DATE_PICKER)
        }
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
            putInt(EVENT_CARD, event.id)
        }
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .navigate(R.id.action_navigation_home_to_navigation_event_card, bundle)
    }

}