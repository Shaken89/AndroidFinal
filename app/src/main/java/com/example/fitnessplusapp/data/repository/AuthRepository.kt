package com.example.fitnessplusapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    
    // тут храним юзеров временно
    private val users = mutableMapOf<String, String>()

    val currentUser: String?
        get() = prefs.getString("current_user", null)

    fun loginUser(email: String, password: String): Result<String> {
        return try {
            val storedPassword = users[email]
            if (storedPassword == password) {
                prefs.edit().putString("current_user", email).apply()
                Result.success(email)
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun registerUser(email: String, password: String): Result<String> {
        return try {
            if (users.containsKey(email)) {
                Result.failure(Exception("Пользователь уже существует"))
            } else {
                users[email] = password
                prefs.edit().putString("current_user", email).apply()
                Result.success(email)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        prefs.edit().remove("current_user").apply()
    }
}
    