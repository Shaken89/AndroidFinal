package com.example.fitnessplusapp.data.repository

import androidx.lifecycle.LiveData
import com.yourpackage.fitnessplus.data.local.dao.WorkoutDao
import com.yourpackage.fitnessplus.data.local.entity.WorkoutEntity

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: LiveData<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    suspend fun insert(workout: WorkoutEntity) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun delete(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }

    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsByCategory(category)
    }
}