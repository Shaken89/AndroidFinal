// File: app/src/main/java/com/example/fitnessplusapp/data/repository/AuthRepository.kt

package com.example.fitnessplusapp.data.repository

import com.example.fitnessplusapp.data.remote.ApiService
import com.example.fitnessplusapp.data.remote.models.AuthRequest
import javax.inject.Inject

// Опять же, @Inject для примера с Hilt/Dagger.
// Если не используете DI, будете передавать ApiService вручную.
class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun loginUser(authRequest: AuthRequest) = apiService.loginUser(authRequest)

    suspend fun registerUser(authRequest: AuthRequest) = apiService.registerUser(authRequest)
}
    