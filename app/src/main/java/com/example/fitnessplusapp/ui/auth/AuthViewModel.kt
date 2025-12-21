package com.example.fitnessplusapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            val result = authRepository.loginUser(email, password)
            if (result.isSuccess) {
                _authState.postValue(AuthState.Success)
            } else {
                _authState.postValue(AuthState.Error(result.exceptionOrNull()?.message ?: "Ошибка входа"))
            }
        }
    }

    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        
        viewModelScope.launch {
            val result = authRepository.registerUser(email, password)
            if (result.isSuccess) {
                _authState.postValue(AuthState.Success)
            } else {
                _authState.postValue(AuthState.Error(result.exceptionOrNull()?.message ?: "Ошибка регистрации"))
            }
        }
    }
}

// состояния для UI
sealed class AuthState {
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
    