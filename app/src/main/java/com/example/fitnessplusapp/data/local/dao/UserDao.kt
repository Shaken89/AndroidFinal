// File: app/src/main/java/com/example/fitnessplusapp/data/local/dao/UserDao.kt
package com.example.fitnessplusapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnessplusapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // --- ИСПРАВЛЕНИЕ ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity) // <-- Убедитесь, что метод ничего не возвращает (Unit)

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getUserProfile(): Flow<UserEntity?>
}
