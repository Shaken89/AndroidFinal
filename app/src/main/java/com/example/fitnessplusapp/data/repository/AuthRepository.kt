package com.example.fitnessplusapp.data.repository


import android.content.SharedPreferences
import com.example.fitnessplusapp.data.local.dao.UserDao
import com.example.fitnessplusapp.data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao,
    private val prefs: SharedPreferences
) {
    val currentUser: String?
        get() = prefs.getString("current_user", null)

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    suspend fun loginUser(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        if (!isValidEmail(email)) {
            return@withContext Result.failure(Exception("Некорректный email"))
        }
        try {
            val user = userDao.getUserByEmail(email)
            if (user != null && user.id == password) {
                prefs.edit().putString("current_user", email).apply()
                Result.success(email)
            } else {
                Result.failure(Exception("Неверный email или пароль"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        if (!isValidEmail(email)) {
            return@withContext Result.failure(Exception("Некорректный email"))
        }
        try {
            val existing = userDao.getUserByEmail(email)
            if (existing != null) {
                Result.failure(Exception("Пользователь уже существует"))
            } else {
                val user = UserEntity(id = password, email = email)
                userDao.insertOrUpdateUser(user)
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
    