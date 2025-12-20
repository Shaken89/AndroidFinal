// File: app/src/main/java/com/example/fitnessplusapp/FitnessPlusApplication.kt

package com.example.fitnessplusapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessPlusApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        Log.d("FitnessPlusApp", "Application started")
        
        // Инициализация WorkManager отложена - запускается по требованию
        // WorkoutNotificationHelper.scheduleDailyReminder(this) // Раскомментируйте когда нужно
    }
}
    