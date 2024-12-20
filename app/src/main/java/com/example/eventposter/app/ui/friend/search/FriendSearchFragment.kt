package com.example.eventposter.app.ui.friend.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventposter.app.ui.adapters.recycler.FriendAdapter
import com.example.eventposter.databinding.FragmentSearchFriendBinding
import com.example.eventposter.domain.UserModel

class FriendSearchFragment : Fragment() {

    private var _binding: FragmentSearchFriendBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var fragment: FriendSearchFragment? = null
        fun getInstance(): FriendSearchFragment {
            if (fragment == null) {
                fragment = FriendSearchFragment()
            }
            return fragment!!
        }
    }

    private lateinit var viewModel: FriendSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[FriendSearchViewModel::class.java]
        _binding = FragmentSearchFriendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvFriendsSearchResult
        val adapter = FriendAdapter(requireContext())

        adapter.friends.add(
            UserModel(
                "User1",
                "https://sun1-96.userapi.com/s/v1/ig2/KBdKwastFMxQ6k9HdHl49wD7UiUinUcoVb9_800NfAZ8r_Cg8u-XW-gFmwh5w3-CCc_pNwohVgs4RNp3FrsFytHV.jpg?quality=95&crop=848,251,801,801&as=32x32,48x48,72x72,108x108,160x160,240x240,360x360,480x480,540x540,640x640,720x720&ava=1&u=7NUvYU647bCq1G90YX55vnE2uy41oZLGPnZVWOwtDqI&cs=200x200"
            )
        )
        adapter.friends.add(
            UserModel(
                "User2",
                "https://sun1-28.userapi.com/s/v1/ig2/3nWI2TmzQM-aqZ5tCQRDYfj_al1xbOwhEzfCRQw6nYe6KKpYCh-LvatYpp_jv09aJCNmQrXL7naVrZSgCy34Qhjt.jpg?quality=95&crop=0,2,1077,1077&as=32x32,48x48,72x72,108x108,160x160,240x240,360x360,480x480,540x540,640x640,720x720&ava=1&cs=50x50"
            )
        )
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}