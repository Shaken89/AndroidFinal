package com.example.fitnessplusapp.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnessplusapp.R
import com.example.fitnessplusapp.data.repository.AuthRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUserName = view.findViewById<TextView>(R.id.tv_user_name)
        val tvUserEmail = view.findViewById<TextView>(R.id.tv_user_email)
        val btnWorkouts = view.findViewById<Button>(R.id.btnWorkouts)
        val btnNutrition = view.findViewById<Button>(R.id.btnNutrition)
        val btnProgress = view.findViewById<Button>(R.id.btnProgress)

        // показываем текущего юзера
        val currentUser = authRepository.currentUser
        if (currentUser != null) {
            tvUserName.text = "Добро пожаловать!"
            tvUserEmail.text = currentUser
        } else {
            tvUserName.text = "Не авторизован"
            tvUserEmail.text = "Войдите в систему"
        }

        // навигация по модулям
        btnWorkouts.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_workoutListFragment)
        }

        btnNutrition.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_nutritionListFragment)
        }

        btnProgress.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_progressFragment)
        }
    }
}
