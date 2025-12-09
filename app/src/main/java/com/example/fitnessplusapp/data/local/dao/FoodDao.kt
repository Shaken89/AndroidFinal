package com.example.fitnessplusapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitnessplusapp.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM food_items ORDER BY date DESC")
    fun getAllFood(): LiveData<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: FoodEntity)

    @Delete
    fun deleteFood(food: FoodEntity)

    @Query("DELETE FROM food_items")
    fun deleteAllFood()
}

