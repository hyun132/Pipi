package com.example.pipi.feature_login.domain.model.dto

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("trainerPhoneNumber") val id: String,
    @SerializedName("trainerPassword") val password: String,
    @SerializedName("trainerName") val name: String
)
