package com.example.fitnessplusapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.local.entity.UserEntity
import com.example.fitnessplusapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Преобразуем Flow<UserEntity?> из репозитория в LiveData<UserEntity?>
    // UI будет автоматически обновляться при изменении данных в локальной базе.
    // ИСПРАВЛЕНИЕ: Явно указываем тип LiveData<UserEntity?>
    val userProfile: LiveData<UserEntity?> = userRepository.userProfile.asLiveData()

    init {
        // При создании ViewModel сразу запрашиваем свежие данные с сервера.
        // Данные обновятся в локальной БД, а LiveData автоматически уведомит UI.
        refreshProfile()
    }

    private fun refreshProfile() {
        viewModelScope.launch {
            userRepository.refreshUserProfile()
        }
    }
}