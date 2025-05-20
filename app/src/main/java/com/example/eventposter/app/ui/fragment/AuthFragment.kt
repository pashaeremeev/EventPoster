package com.example.eventposter.app.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewmodel.AuthViewModel
import com.example.eventposter.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private var isAuth: Boolean = true

    companion object {
        private var fragment: AuthFragment? = null
        fun getInstance(): AuthFragment {
            if (fragment != null) {
                fragment = AuthFragment()
            }
            return fragment!!
        }
    }

    private lateinit var vm: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[AuthViewModel::class.java]
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        binding.toRegActionButton.setOnClickListener {
            if (isAuth) {
                binding.tvAuthLabel.text = "Регистрация"
                binding.authButton.text = "Зарегистрироваться"
                binding.toRegActionButton.text = "Уже есть учётная запись"
            } else {
                binding.tvAuthLabel.text = "Вход в профиль"
                binding.authButton.text = "Войти"
                binding.toRegActionButton.text = "Ещё нет учётной записи"
            }
            isAuth = !isAuth
        }

        binding.ivBackAuth.setOnClickListener {
            navigateTo(R.id.action_navigation_auth_to_navigation_profile)
        }

        return binding.root
    }

    private fun navigateTo(resourceId: Int) {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(resourceId)
    }

}