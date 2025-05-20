package com.example.eventposter.app.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventposter.app.ui.adapters.PageAdapter
import com.example.eventposter.app.ui.viewmodel.SearchViewModel
import com.example.eventposter.databinding.FragmentSearchBinding
import com.example.eventposter.domain.model.FilterEventModel
import com.example.eventposter.domain.model.FilterUserModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val tabNames = arrayOf("Мероприятия", "Пользователи")

    private val binding get() = _binding!!

    companion object {
        private var fragment: SearchFragment? = null
        const val FILTER_TAG = "FILTER_TAG"
        fun getInstance(): SearchFragment {
            if (fragment == null) {
                fragment = SearchFragment()
            }
            return fragment!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm =
            ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val tabLayout = binding.tlProfileScrollSearchFragments
        val viewPager = binding.vpProfileViewPager

        val adapter = PageAdapter(childFragmentManager, lifecycle)

        adapter.fragments = arrayListOf(
                EventsSearchFragment.getInstance(),
                UserSearchFragment.getInstance()
            )

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabNames[position]
        }.attach()

        binding.etSearchText.addTextChangedListener(object : TextWatcher {
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

        binding.ivFilter.setOnClickListener {
            val filterFragment: FilterFragment
            when (viewPager.currentItem) {
                0 -> {
                    filterFragment = FilterEventFragment()
                    vm.eventFilter.let { filterFragment.loadFilterSettings(it) }
                }
                else -> {
                    filterFragment = FilterUserFragment()
                    vm.userFilter.let { filterFragment.loadFilterSettings(it) }
                }
            }
            if (parentFragmentManager.findFragmentByTag(FILTER_TAG) == null) {
                filterFragment.show(parentFragmentManager, FILTER_TAG)
            }
            filterFragment.onFilterApplied = { settings ->
                adapter.setSearchSettings(settings, viewPager.currentItem)
                when(settings) {
                    is FilterEventModel -> vm.eventFilter = settings
                    is FilterUserModel -> vm.userFilter = settings
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}