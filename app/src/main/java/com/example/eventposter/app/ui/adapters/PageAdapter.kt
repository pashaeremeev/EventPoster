package com.example.eventposter.app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter
(
    fragmentManager: FragmentManager?,
    lifecycle: Lifecycle?
) : FragmentStateAdapter(fragmentManager!!, lifecycle!!) {

    private var fragments = arrayListOf<Fragment>()

    fun setFragments(value: ArrayList<Fragment>) {
        this.fragments = value
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}