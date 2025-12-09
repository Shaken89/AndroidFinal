package com.example.fitnessplusapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val calories: Int,
    val mealType: String,
    val date: Long = System.currentTimeMillis()
)


