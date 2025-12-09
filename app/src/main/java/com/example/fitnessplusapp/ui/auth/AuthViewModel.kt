// File: app/src/main/java/com/example/fitnessplusapp/ui/auth/AuthViewModel.kt
package com.example.fitnessplusapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.preferences.UserPreferences
import com.example.fitnessplusapp.data.remote.models.AuthRequest
import com.example.fitnessplusapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Аннотируем ViewModel с помощью @HiltViewModel
@HiltViewModel
class AuthViewModel @Inject constructor(
    // 2. Hilt автоматически предоставит нам экземпляры репозитория и менеджера преференсов
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // LiveData для отслеживания состояния UI (загрузка, успех, ошибка)
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun login(email: String, password: String) {
        // Устанавливаем состояние загрузки
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(AuthRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    // Если успех, сохраняем токен
                    response.body()?.token?.let { token ->
                        userPreferences.saveAuthToken(token)
                        // Устанавливаем состояние успеха
                        _authState.postValue(AuthState.Success)
                    }
                } else {
                    // Устанавливаем состояние ошибки с сообщением от сервера
                    _authState.postValue(AuthState.Error("Ошибка входа: ${response.message()}"))
                }
            } catch (e: Exception) {
                // Устанавливаем состояние ошибки при проблемах с сетью
                _authState.postValue(AuthState.Error("Сетевая ошибка: ${e.message}"))
            }
        }
    }

    // Аналогичная функция для регистрации
    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(AuthRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.token?.let { token ->
                        userPreferences.saveAuthToken(token)
                        _authState.postValue(AuthState.Success)
                    }
                } else {
                    _authState.postValue(AuthState.Error("Ошибка регистрации: ${response.message()}"))
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Сетевая ошибка: ${e.message}"))
            }
        }
    }
}

// Вспомогательный класс для представления состояний UI
sealed class AuthState {
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
    