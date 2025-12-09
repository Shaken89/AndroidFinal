package com.example.fitnessplusapp.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessplusapp.data.local.dao.FoodDao
import com.example.fitnessplusapp.data.local.entity.FoodEntity

class NutritionRepository(
    private val foodDao: FoodDao
) {

    fun getAllFood(): LiveData<List<FoodEntity>> = foodDao.getAllFood()

    fun addFood(
        name: String,
        calories: Int,
        mealType: String,
        date: Long
    ) {
        val food = FoodEntity(
            name = name,
            calories = calories,
            mealType = mealType,
            date = date
        )
        foodDao.insertFood(food)
    }

    fun deleteFood(food: FoodEntity) {
        foodDao.deleteFood(food)
    }
}

