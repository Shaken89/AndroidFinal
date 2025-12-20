package com.example.fitnessplusapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 2, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile private var INSTANCE: WorkoutDatabase? = null

        // Миграция с версии 1 на версию 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Добавляем новые колонки
                db.execSQL("ALTER TABLE workouts ADD COLUMN notes TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE workouts ADD COLUMN intensity TEXT NOT NULL DEFAULT 'Medium'")
                db.execSQL("ALTER TABLE workouts ADD COLUMN sets INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE workouts ADD COLUMN reps INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE workouts ADD COLUMN imageUri TEXT DEFAULT NULL")
                db.execSQL("ALTER TABLE workouts ADD COLUMN completed INTEGER NOT NULL DEFAULT 1")
            }
        }

        fun getDatabase(context: Context): WorkoutDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration() // Пересоздать БД если миграция не проходит
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
