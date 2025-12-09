// File: app/src/main/java/com/example/fitnessplusapp/data/local/WorkoutDatabase.kt
package com.example.fitnessplusapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity

// Убираем UserEntity из списка entities
@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    // Убираем abstract fun userDao()

    companion object {
        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDatabase(context: Context): WorkoutDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
