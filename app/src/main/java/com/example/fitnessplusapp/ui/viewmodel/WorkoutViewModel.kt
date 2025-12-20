package com.example.fitnessplusapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.local.entity.WorkoutEntity
import com.example.fitnessplusapp.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    val allWorkouts: LiveData<List<WorkoutEntity>> = repository.allWorkouts

    fun insert(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.insert(workout)
        }
    }

    fun delete(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.delete(workout)
        }
    }

    fun getWorkoutsByCategory(category: String): LiveData<List<WorkoutEntity>> {
        return repository.getWorkoutsByCategory(category)
    }
}