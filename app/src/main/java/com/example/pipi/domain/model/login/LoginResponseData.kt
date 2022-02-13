package com.example.pipi.domain.model.login

data class LoginResponseData(
    val createdAt: String,
    val trainerName: String,
    val trainerPassword: String,
    val trainerPhoneNumber: String,
    val updatedAt: String
)