package com.example.fitnessplusapp.data.repository

import androidx.lifecycle.LiveData
import com.example.fitnessplusapp.data.local.dao.FoodDao
import com.example.fitnessplusapp.data.local.entity.FoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NutritionRepository @Inject constructor(
    private val foodDao: FoodDao
) {

    fun getAllFood(): LiveData<List<FoodEntity>> = foodDao.getAllFood()

    // добавление новой еды
    suspend fun addFood(
        name: String,
        calories: Int,
        mealType: String,
        date: Long
    ) = withContext(Dispatchers.IO) {
        val food = FoodEntity(
            name = name,
            calories = calories,
            mealType = mealType,
            date = date
        )
        foodDao.insertFood(food)
    }

    suspend fun deleteFood(food: FoodEntity) = withContext(Dispatchers.IO) {
        foodDao.deleteFood(food)
    }
}

