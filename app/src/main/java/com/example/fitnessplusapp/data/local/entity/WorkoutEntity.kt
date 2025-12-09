package com.example.fitnessplusapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val duration: Int,
    val caloriesBurned: Int,
    val date: Long = System.currentTimeMillis()
)