// File: app/src/main/java/com/example/fitnessplusapp/data/local/entity/UserEntity.kt

package com.example.fitnessplusapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val email: String,
    val age: Int?,
    val weight: Double?,
    val height: Double?
)
