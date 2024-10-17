package com.example.eventposter.app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eventposter.app.ui.search.SearchFragment

class ProfilePageAdapter(fragmentManager: FragmentManager?, lifecycle: Lifecycle?) :
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    override fun createFragment(position: Int): Fragment {
        return SearchFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }

}