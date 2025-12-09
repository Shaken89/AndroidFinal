// File: app/src/main/java/com/example/fitnessplusapp/data/repository/UserRepository.kt
package com.example.fitnessplusapp.data.repository

import com.example.fitnessplusapp.data.local.dao.UserDao
import com.example.fitnessplusapp.data.remote.ApiService
import javax.inject.Inject
import com.example.fitnessplusapp.data.local.entity.UserEntity

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    // Получаем профиль из локальной БД.
    // Этот Flow будет "слушать" изменения в таблице user_profile.
    val userProfile = userDao.getUserProfile()

    // Функция для обновления профиля с сервера.
    suspend fun refreshUserProfile() {
        try {
            val response = apiService.getProfile()
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    // Конвертируем сетевую модель в сущность БД и сохраняем
                    // (В данном случае они идентичны, но в реальных проектах могут отличаться)
                    val userEntity = UserEntity(
                        id = user.id,
                        name = user.name,
                        email = user.email,
                        age = user.age,
                        weight = user.weight,
                        height = user.height
                    )
                    userDao.insertOrUpdateUser(userEntity)
                }
            }
        } catch (e: Exception) {
            // Обработка ошибок сети
        }
    }
}
    