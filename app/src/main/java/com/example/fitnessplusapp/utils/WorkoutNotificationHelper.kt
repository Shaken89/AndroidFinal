package com.example.fitnessplusapp.utils

import android.content.Context
import androidx.work.*
import com.example.fitnessplusapp.data.workers.WorkoutReminderWorker
import java.util.concurrent.TimeUnit

object WorkoutNotificationHelper {

    private const val DAILY_REMINDER_WORK_NAME = "daily_workout_reminder"

    fun scheduleDailyReminder(context: Context, hourOfDay: Int = 9) {
        // Рассчитываем начальную задержку до первого запуска
        val currentTime = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hourOfDay)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            
            // Если время уже прошло сегодня, планируем на завтра
            if (timeInMillis <= currentTime) {
                add(java.util.Calendar.DAY_OF_YEAR, 1)
            }
        }
        
        val initialDelay = calendar.timeInMillis - currentTime

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false)
            .build()

        val dailyWorkRequest = PeriodicWorkRequestBuilder<WorkoutReminderWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            DAILY_REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            dailyWorkRequest
        )
    }

    fun cancelDailyReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(DAILY_REMINDER_WORK_NAME)
    }

    fun isReminderScheduled(context: Context): Boolean {
        val workInfos = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork(DAILY_REMINDER_WORK_NAME).get()
        return workInfos.any { !it.state.isFinished }
    }
}
