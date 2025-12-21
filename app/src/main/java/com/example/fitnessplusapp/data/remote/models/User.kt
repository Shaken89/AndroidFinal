package com.example.fitnessplusapp.data.remote.models

data class User(
    val id: String,
    val name: String?,
    val email: String,
    val age: Int?,
    val weight: Double?,
    val height: Double?
)
    