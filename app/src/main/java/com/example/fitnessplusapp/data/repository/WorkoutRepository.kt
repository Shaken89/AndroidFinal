package com.example.fitnessplusapp.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity
import java.util.concurrent.Executors

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    private val executor = Executors.newSingleThreadExecutor()
    val allWorkouts: LiveData<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    fun insert(workout: WorkoutEntity) {
        executor.execute {
            workoutDao.insertWorkout(workout)
        }
    }

    fun delete(workout: WorkoutEntity) {
        executor.execute {
            workoutDao.deleteWorkout(workout)
        }
    }

    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsByCategory(category)
    }
}