package com.example.fitnessplusapp.ui.nutrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessplusapp.data.local.entity.FoodEntity
import com.example.fitnessplusapp.data.repository.NutritionRepository

class NutritionViewModel(
    private val repository: NutritionRepository
) : ViewModel() {

    val foodList: LiveData<List<FoodEntity>> = repository.getAllFood()

    fun addFood(name: String, calories: Int, mealType: String, date: Long) {
        repository.addFood(name, calories, mealType, date)
    }

    fun deleteFood(food: FoodEntity) {
        repository.deleteFood(food)
    }
}

