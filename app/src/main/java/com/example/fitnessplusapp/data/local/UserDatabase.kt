// File: app/src/main/java/com/example/fitnessplusapp/data/local/UserDatabase.kt
package com.example.fitnessplusapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fitnessplusapp.data.local.dao.UserDao
import com.example.fitnessplusapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database" // Даем базе данных свое уникальное имя
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
