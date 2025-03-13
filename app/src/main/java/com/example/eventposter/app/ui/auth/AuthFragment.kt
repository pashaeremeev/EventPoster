package com.example.eventposter.app.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.eventposter.R
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
        val root = binding.root
        val toRegView = binding.toRegActionButton
        val ivBack = binding.ivBackAuth
        val tvAuthLabel = binding.tvAuthLabel
        val authButton = binding.authButton
        toRegView.setOnClickListener {
            if (isAuth) {
                tvAuthLabel.text = "Регистрация"
                authButton.text = "Зарегистрироваться"
                toRegView.text = "Уже есть учётная запись"
            } else {
                tvAuthLabel.text = "Вход в профиль"
                authButton.text = "Войти"
                toRegView.text = "Ещё нет учётной записи"
            }
            isAuth = !isAuth
        }
        ivBack.setOnClickListener {
            navigateTo(R.id.action_navigation_auth_to_navigation_profile)
        }
        return root
    }

    private fun navigateTo(resourceId: Int) {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(resourceId)
    }

}