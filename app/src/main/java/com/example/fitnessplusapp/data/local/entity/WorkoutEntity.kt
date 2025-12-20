package com.example.fitnessplusapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val duration: Int, // в минутах
    val caloriesBurned: Int,
    val date: Long = System.currentTimeMillis(),
    
    // Новые поля для Final
    val notes: String = "", // заметки о тренировке
    val intensity: String = "Medium", // Low, Medium, High
    val sets: Int = 0, // количество подходов
    val reps: Int = 0, // количество повторений
    val imageUri: String? = null, // путь к фото тренировки
    val completed: Boolean = true // выполнена ли тренировка
)
