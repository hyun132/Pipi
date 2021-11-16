package com.example.pipi.data.response


data class SignUpResponse(
    val `data`: SignUpResponseData,
    val message: String,
    val status: Int,
    val success: Boolean
)