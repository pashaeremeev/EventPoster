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
import com.google.android.material.transition.MaterialFadeThrough

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private var isAuth: Boolean = true

    companion object {
        fun getInstance(): AuthFragment = AuthFragment()
    }

    private lateinit var vm: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = 300
        }
        exitTransition = MaterialFadeThrough().apply {
            duration = 200
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[AuthViewModel::class.java]
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUIForAuthState(isAuth)

        binding.toRegActionButton.setOnClickListener {
            isAuth = !isAuth
            updateUIForAuthState(isAuth)
        }

        binding.ivBackAuth.setOnClickListener {
            navigateTo(R.id.action_navigation_auth_to_navigation_profile)
        }

        binding.smRoleReg.setOnCheckedChangeListener { _, isChecked ->
            updateNameHint(isChecked)
        }
    }

    private fun updateUIForAuthState(isAuthMode: Boolean) {
        if (isAuthMode) {
            binding.tvAuthLabel.text = "Вход в профиль"
            binding.authButton.text = "Войти"
            binding.toRegActionButton.text = "Ещё нет учётной записи"

            binding.tilRegName.visibility = View.INVISIBLE
            binding.regRoleContainer.visibility = View.INVISIBLE
        } else {
            binding.tvAuthLabel.text = "Регистрация"
            binding.authButton.text = "Зарегистрироваться"
            binding.toRegActionButton.text = "Уже есть учётная запись"

            binding.regRoleContainer.visibility = View.VISIBLE

            updateNameHint(binding.smRoleReg.isChecked)
        }
    }

    private fun updateNameHint(isOrganizer: Boolean) {
        binding.tilRegName.hint = if (isOrganizer) {
            "Наименование организации"
        } else {
            "Имя пользователя"
        }

        binding.tilRegName.visibility = if (isAuth) View.INVISIBLE else View.VISIBLE
    }

    private fun navigateTo(resourceId: Int) {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(resourceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}