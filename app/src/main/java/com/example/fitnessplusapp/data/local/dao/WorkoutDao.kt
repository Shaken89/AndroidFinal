package com.example.fitnessplusapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE category = :category ORDER BY date DESC")
    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getWorkoutsByDateRange(startDate: Long, endDate: Long): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE name LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%'")
    fun searchWorkouts(query: String): LiveData<List<WorkoutEntity>>

    @Query("SELECT SUM(duration) FROM workouts")
    fun getTotalDuration(): LiveData<Int?>

    @Query("SELECT SUM(caloriesBurned) FROM workouts")
    fun getTotalCalories(): LiveData<Int?>

    @Query("SELECT COUNT(*) FROM workouts")
    fun getTotalWorkoutsCount(): LiveData<Int>

    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getWorkoutById(id: Int): WorkoutEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts()
}
