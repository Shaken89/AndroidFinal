// File: app/src/main/java/com/example/fitnessplusapp/ui/profile/ProfileFragment.kt
package com.example.fitnessplusapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userProfile.observe(viewLifecycleOwner) { user ->
            // user может быть null, если в базе еще нет данных
            if (user != null) {
                binding.tvUserName.text = "Имя: ${user.name ?: "Не указано"}"
                binding.tvUserEmail.text = "Email: ${user.email}"
                // Обновите остальные TextView по аналогии
            } else {
                binding.tvUserName.text = "Загрузка данных профиля..."
                binding.tvUserEmail.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
