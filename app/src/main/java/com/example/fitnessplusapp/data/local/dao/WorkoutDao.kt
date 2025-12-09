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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkout(workout: WorkoutEntity)

    @Delete
    fun deleteWorkout(workout: WorkoutEntity)

    @Query("DELETE FROM workouts")
    fun deleteAllWorkouts()
}