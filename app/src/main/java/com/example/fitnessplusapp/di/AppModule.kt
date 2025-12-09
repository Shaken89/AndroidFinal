// File: app/src/main/java/com/example/fitnessplusapp/di/AppModule.kt
package com.example.fitnessplusapp.di

import android.content.Context
import com.example.fitnessplusapp.data.local.UserDatabase
import com.example.fitnessplusapp.data.local.WorkoutDatabase
import com.example.fitnessplusapp.data.local.dao.UserDao
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // --- Провайдеры для Workout Database ---

    @Provides
    @Singleton // Singleton гарантирует, что будет создан только один экземпляр базы данных
    fun provideWorkoutDatabase(@ApplicationContext context: Context): WorkoutDatabase {
        return WorkoutDatabase.getDatabase(context)
    }

    @Provides
    fun provideWorkoutDao(database: WorkoutDatabase): WorkoutDao {
        // Hilt автоматически возьмет WorkoutDatabase из метода выше
        return database.workoutDao()
    }


    // --- Провайдеры для User Database ---

    @Provides
    @Singleton // Singleton для второй базы данных
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao {
        // Hilt автоматически возьмет UserDatabase из метода выше
        return database.userDao()
    }
}
