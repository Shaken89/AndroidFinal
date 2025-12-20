package com.example.fitnessplusapp.ui.nutrition

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessplusapp.data.local.entity.FoodEntity
import com.example.fitnessplusapp.data.repository.NutritionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val repository: NutritionRepository
) : ViewModel() {

    val foodList: LiveData<List<FoodEntity>> = repository.getAllFood()

    // работа с едой через viewModelScope
    fun addFood(name: String, calories: Int, mealType: String, date: Long) {
        viewModelScope.launch {
            repository.addFood(name, calories, mealType, date)
        }
    }

    fun deleteFood(food: FoodEntity) {
        viewModelScope.launch {
            repository.deleteFood(food)
        }
    }
}

