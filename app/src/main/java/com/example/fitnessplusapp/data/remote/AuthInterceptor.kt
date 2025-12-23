package com.example.fitnessplusapp.data.remote

import com.example.fitnessplusapp.data.preferences.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {

    // добавляем токен к запросам
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // runBlocking может вызвать проблемы с производительностью, но для простоты оставим его здесь.
        // В идеальном мире это должно быть обработано асинхронно.
        val token = runBlocking { userPreferences.authToken.first() }
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}
