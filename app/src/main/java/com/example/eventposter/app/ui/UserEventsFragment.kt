package com.example.eventposter.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.ui.adapters.PageAdapter
import com.example.eventposter.app.ui.event.search.EventsSearchFragment
import com.example.eventposter.app.ui.search.SearchViewModel
import com.example.eventposter.databinding.FragmentUserEventsBinding
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.model.FilterUserModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserEventsFragment : Fragment() {

    private var _binding: FragmentUserEventsBinding? = null
    private val tabNames = arrayOf("Мои", "Избранные", "Билеты")

    private val binding get() = _binding!!

    private lateinit var vm: SearchViewModel

    companion object {
        private var fragment: UserEventsFragment? = null
        const val FILTER_TAG = "FILTER_TAG"
        fun getInstance(): UserEventsFragment {
            if (fragment == null) {
                fragment = UserEventsFragment()
            }
            return fragment!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentUserEventsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tlUserEvents
        val viewPager = binding.vpUserEvents

        val adapter = PageAdapter(childFragmentManager, lifecycle)

        adapter.fragments = arrayListOf(
            EventsSearchFragment(),
            EventsSearchFragment(),
            EventsSearchFragment()
        )

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabNames[position]
        }.attach()

        binding.etSearchUserEventsText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.setSearchQuery(s.toString())
                vm.eventFilter.text = s.toString()
                vm.userFilter.name = s.toString()
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        binding.ivUserEventsFilter.setOnClickListener {
            val filterFragment = FilterEventFragment()
            vm.eventFilter.let {
                filterFragment.loadFilterSettings(it)
                if (parentFragmentManager.findFragmentByTag(FILTER_TAG) == null) {
                    filterFragment.show(parentFragmentManager, FILTER_TAG)
                }
                filterFragment.onFilterApplied = { settings ->
                    adapter.setSearchSettings(settings, viewPager.currentItem)
                    when (settings) {
                        is FilterEventModel -> vm.eventFilter = settings
                        is FilterUserModel -> vm.userFilter = settings
                    }
                }
            }
        }

        binding.btnAddEvent.setOnClickListener {
            openAddEventView()
        }

        binding.ivBackUserEvents.setOnClickListener {
            navigateTo(R.id.action_navigation_user_events_to_navigation_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateTo(resourceId: Int) {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(resourceId)
    }

    private fun openAddEventView() {
        navigateTo(R.id.action_navigation_user_events_to_navigation_form_event)
    }
}