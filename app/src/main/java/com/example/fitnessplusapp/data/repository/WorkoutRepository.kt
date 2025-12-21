package com.example.fitnessplusapp.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessplusapp.data.local.dao.WorkoutDao
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao
) {
    // получаем все тренировки из БД
    val allWorkouts: LiveData<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    suspend fun insert(workout: WorkoutEntity) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun update(workout: WorkoutEntity) {
        workoutDao.updateWorkout(workout)
    }

    suspend fun delete(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun getWorkoutById(id: Int): WorkoutEntity? {
        return workoutDao.getWorkoutById(id)
    }

    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsByCategory(category)
    }

    fun getWorkoutsByDateRange(startDate: Long, endDate: Long): LiveData<List<WorkoutEntity>> {
        return workoutDao.getWorkoutsByDateRange(startDate, endDate)
    }

    fun searchWorkouts(query: String): LiveData<List<WorkoutEntity>> {
        return workoutDao.searchWorkouts(query)
    }

    fun getTotalDuration(): LiveData<Int?> {
        return workoutDao.getTotalDuration()
    }

    fun getTotalCalories(): LiveData<Int?> {
        return workoutDao.getTotalCalories()
    }

    fun getTotalWorkoutsCount(): LiveData<Int> {
        return workoutDao.getTotalWorkoutsCount()
    }
}