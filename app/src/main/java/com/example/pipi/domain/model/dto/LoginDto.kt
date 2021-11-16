package com.example.pipi.domain.model.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("trainerPhoneNumber") val phone:String,
    @SerializedName("trainerPassword") val password:String
)
