package com.example.eventposter.app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eventposter.app.Searchable

class PageAdapter
(
    fragmentManager: FragmentManager?,
    lifecycle: Lifecycle?
) : FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    var fragments = listOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun setSearchQuery(text: String) {
        fragments.forEach {
            if (it is Searchable) {
                it.onQueryChanged(text)
            }
        }
    }

}