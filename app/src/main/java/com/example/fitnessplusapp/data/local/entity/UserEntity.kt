package com.example.fitnessplusapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String? = null,
    val email: String,
    val age: Int? = null,
    val weight: Double? = null,
    val height: Double? = null
)
