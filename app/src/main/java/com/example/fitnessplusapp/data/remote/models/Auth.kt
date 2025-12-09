package com.example.fitnessplusapp.data.remote.models

// Модель для отправки данных при регистрации и входе
data class AuthRequest(
    val email: String,
    val password: String
)

// Модель для получения ответа от сервера с токеном
data class AuthResponse(
    val token: String
)