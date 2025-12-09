// File: app/src/main/java/com/example/fitnessplusapp/data/remote/models/User.kt

package com.example.fitnessplusapp.data.remote.models

// Эта модель будет использоваться для получения данных с сервера
// и для отображения в UI
data class User(
    val id: String,
    val name: String?,
    val email: String,
    val age: Int?,
    val weight: Double?,
    val height: Double?
)
    