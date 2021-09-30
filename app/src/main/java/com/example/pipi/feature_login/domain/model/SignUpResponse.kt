package com.example.pipi.feature_login.domain.model


data class SignUpResponse(
    val `data`: SignUpResponseData,
    val message: String,
    val status: Int,
    val success: Boolean
)