package com.example.pipi.domain.model.signup

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("trainerPhoneNumber") val id: String,
    @SerializedName("trainerPassword") val password: String,
    @SerializedName("trainerName") val name: String
)
