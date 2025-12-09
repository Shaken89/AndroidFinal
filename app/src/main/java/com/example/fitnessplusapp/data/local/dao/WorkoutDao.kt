// File: app/src/main/java/com/example/fitnessplusapp/data/local/dao/WorkoutDao.kt
package com.example.fitnessplusapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity

@Dao
interface WorkoutDao {

    // Функции чтения остаются без suspend, т.к. возвращают LiveData/Flow
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE category = :category ORDER BY date DESC")
    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>>

    // --- НАЧАЛО ИСПРАВЛЕНИЙ ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity) // <-- ДОБАВИТЬ suspend

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity) // <-- ДОБАВИТЬ suspend

    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts() // <-- ДОБАВИТЬ suspend и изменить возвращаемый тип на Unit (неявный)
}
