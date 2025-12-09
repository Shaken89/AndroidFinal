// File: app/src/main/java/com/example/fitnessplusapp/data/remote/AuthInterceptor.kt

package com.example.fitnessplusapp.data.remote

import com.example.fitnessplusapp.data.preferences.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject // Используем аннотацию, если планируете внедрение зависимостей

// Примечание: @Inject здесь для примера с Hilt. Если не используете DI,
// будете передавать UserPreferences через конструктор вручную.
class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Получаем токен. runBlocking здесь допустим, т.к. interceptor
        // не может быть suspend-функцией, а операция быстрая.
        val token = runBlocking { userPreferences.authToken.first() }

        // Создаем новый запрос с заголовком Authorization
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}
    