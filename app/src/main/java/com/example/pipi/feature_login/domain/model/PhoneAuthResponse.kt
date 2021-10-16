package com.example.pipi.feature_login.domain.model


import com.google.gson.annotations.SerializedName

data class PhoneAuthResponse(
    val message: String,
    val status: Double,
    val success: Boolean
)