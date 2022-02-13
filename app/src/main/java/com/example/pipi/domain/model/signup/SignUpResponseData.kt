package com.example.pipi.domain.model.signup


data class SignUpResponseData(
    val createdAt: String,
    val trainerName: String,
    val trainerPassword: String,
    val trainerPhoneNumber: String,
    val updatedAt: String
)