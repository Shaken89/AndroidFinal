package com.example.fitnessplusapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Создаем экземпляр DataStore на уровне всего приложения
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        // Ключ для хранения JWT токена
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    // Функция для сохранения токена
    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    // Flow для получения токена в реальном времени
    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN_KEY]
    }

    // Функция для очистки токена (при выходе из аккаунта)
    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
    