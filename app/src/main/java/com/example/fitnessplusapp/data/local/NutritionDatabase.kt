package com.example.fitnessplusapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessplusapp.data.local.dao.FoodDao
import com.example.fitnessplusapp.data.local.entity.FoodEntity

@Database(
    entities = [FoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NutritionDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: NutritionDatabase? = null

        fun getDatabase(context: Context): NutritionDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NutritionDatabase::class.java,
                    "nutrition_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
