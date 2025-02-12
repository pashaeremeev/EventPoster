package com.example.eventposter.app.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eventposter.app.ui.adapters.PageAdapter
import com.example.eventposter.app.ui.event.search.EventsSearchFragment
import com.example.eventposter.app.ui.friend.search.FriendSearchFragment
import com.example.eventposter.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val tabNames = arrayOf("Мероприятия", "Пользователи")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        private var fragment: SearchFragment? = null
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
        val root: View = binding.root

        val tabLayout = binding.tlProfileScrollSearchFragments
        val viewPager = binding.vpProfileViewPager

        val adapter = PageAdapter(childFragmentManager, lifecycle)

        adapter.fragments = arrayListOf(
                EventsSearchFragment.getInstance(),
                FriendSearchFragment.getInstance()
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
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}