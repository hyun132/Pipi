package com.example.pipi.data.response


data class LoginResponse(
    val `data`: LoginResponseData,
    val message: String,
    val status: Double,
    val success: Boolean
)