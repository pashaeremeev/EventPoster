package com.example.eventposter.app.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.eventposter.app.Searchable
import com.example.eventposter.app.ui.adapters.recycler.FriendAdapter
import com.example.eventposter.app.ui.viewmodel.UserSearchViewModel
import com.example.eventposter.databinding.FragmentSearchUserBinding
import com.example.eventposter.domain.model.FilterUserModel
import kotlinx.coroutines.launch

class UserSearchFragment : Fragment(), Searchable {

    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var fragment: UserSearchFragment? = null
        fun getInstance(): UserSearchFragment {
            if (fragment == null) {
                fragment = UserSearchFragment()
            }
            return fragment!!
        }
    }

    private lateinit var vm: UserSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this)[UserSearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FriendAdapter(requireContext())

        binding.rvFriendsSearchResult.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.users.collect { users ->
                    val diff = adapter.setUsers(users = users)
                    diff.dispatchUpdatesTo(adapter)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFilterChanged(newSettings: FilterModel) {
        if (::vm.isInitialized && newSettings is FilterUserModel) {
            vm.updateFilter{ newSettings.copy() }
        }
    }

    override fun onFilterChanged(newText: String) {
        if (::vm.isInitialized) {
            vm.updateFilter{ copy(name = newText) }
        }
    }
}