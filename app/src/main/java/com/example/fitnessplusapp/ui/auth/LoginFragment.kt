// File: app/src/main/java/com/example/fitnessplusapp/ui/auth/LoginFragment.kt
package com.example.fitnessplusapp.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Email и пароль не могут быть пустыми", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        binding.btnGoToRegister.setOnClickListener {
            // Переход на экран регистрации. Убедитесь, что action с таким id есть в nav_graph.xml
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.authState.observe(viewLifecycleOwner) { state ->
            // Управляем видимостью всех интерактивных элементов и прогресс-бара
            binding.progressBar.isVisible = state is AuthState.Loading
            binding.btnLogin.isEnabled = state !is AuthState.Loading
            binding.btnGoToRegister.isEnabled = state !is AuthState.Loading

            when (state) {
                is AuthState.Success -> {
                    Toast.makeText(requireContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show()
                    // Переход на экран профиля. Убедитесь, что action с таким id есть в nav_graph.xml
                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                }
                is AuthState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is AuthState.Loading -> { /* Состояние загрузки уже обработано выше */ }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
