package com.example.fitnessplusapp.di

import android.content.Context
import com.example.fitnessplusapp.data.local.UserDatabase
import com.example.fitnessplusapp.data.local.WorkoutDatabase
import com.example.fitnessplusapp.data.local.NutritionDatabase
import com.example.fitnessplusapp.data.local.dao.UserDao
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import com.example.fitnessplusapp.data.local.dao.FoodDao
import com.example.fitnessplusapp.data.preferences.UserPreferences
import com.example.fitnessplusapp.data.remote.ApiService
import com.example.fitnessplusapp.data.remote.AuthInterceptor
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): android.content.SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // провайдеры для баз данных
    
    @Provides
    @Singleton
    fun provideWorkoutDatabase(@ApplicationContext context: Context): WorkoutDatabase =
        WorkoutDatabase.getDatabase(context)

    @Provides
    fun provideWorkoutDao(db: WorkoutDatabase): WorkoutDao = db.workoutDao()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        UserDatabase.getDatabase(context)

    @Provides
    fun provideUserDao(db: UserDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideNutritionDatabase(@ApplicationContext context: Context): NutritionDatabase =
        NutritionDatabase.getDatabase(context)

    @Provides
    fun provideFoodDao(db: NutritionDatabase): FoodDao = db.foodDao()
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences =
        UserPreferences(context)

    // настройка retrofit и okhttp
    
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Замените на ваш реальный URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
