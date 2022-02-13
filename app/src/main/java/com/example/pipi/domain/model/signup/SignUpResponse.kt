package com.example.pipi.domain.model.signup


data class SignUpResponse(
    val `data`: SignUpResponseData,
    val message: String,
    val status: Int,
    val success: Boolean
)