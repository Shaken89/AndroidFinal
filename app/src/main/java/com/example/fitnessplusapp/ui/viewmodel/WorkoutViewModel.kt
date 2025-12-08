package com.example.fitnessplusapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.local.WorkoutDatabase
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity
import com.example.fitnessplusapp.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutRepository
    val allWorkouts: LiveData<List<WorkoutEntity>>

    init {
        val workoutDao = WorkoutDatabase.getDatabase(application).workoutDao()
        repository = WorkoutRepository(workoutDao)
        allWorkouts = repository.allWorkouts
    }

    fun insert(workout: WorkoutEntity) = viewModelScope.launch {
        repository.insert(workout)
    }

    fun delete(workout: WorkoutEntity) = viewModelScope.launch {
        repository.delete(workout)
    }

    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>> {
        return repository.getWorkoutsByCategory(category)
    }
}