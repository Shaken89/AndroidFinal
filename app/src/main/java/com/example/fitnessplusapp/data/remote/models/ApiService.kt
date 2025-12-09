// File: app/src/main/java/com/example/fitnessplusapp/data/remote/ApiService.kt

package com.example.fitnessplusapp.data.remote

import com.example.fitnessplusapp.data.remote.models.AuthRequest
import com.example.fitnessplusapp.data.remote.models.AuthResponse
import com.example.fitnessplusapp.data.remote.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    // --- Аутентификация ---

    @POST("api/auth/register") // Замените на ваш реальный эндпоинт
    suspend fun registerUser(@Body request: AuthRequest): Response<AuthResponse>

    @POST("api/auth/login") // Замените на ваш реальный эндпоинт
    suspend fun loginUser(@Body request: AuthRequest): Response<AuthResponse>

    // --- Профиль пользователя ---

    @GET("api/profile") // Замените на ваш реальный эндпоинт
    suspend fun getProfile(): Response<User>

    @PUT("api/profile") // Замените на ваш реальный эндпоинт
    suspend fun updateProfile(@Body profile: User): Response<User>
}
    