package com.example.pipi.feature_login.domain.model


data class LoginResponse(
    val `data`: LoginResponseData,
    val message: String,
    val status: Int,
    val success: Boolean
)