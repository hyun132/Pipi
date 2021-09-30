package com.example.pipi.feature_login.domain.model


data class SignUpResponseData(
    val createdAt: String,
    val trainerName: String,
    val trainerPassword: String,
    val trainerPhoneNumber: String,
    val updatedAt: String
)