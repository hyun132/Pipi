package com.example.pipi.domain.model.login


data class LoginResponse(
    val `data`: LoginResponseData,
    val message: String,
    val status: Double,
    val success: Boolean
)