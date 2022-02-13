package com.example.pipi.domain.model.login

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("trainerPhoneNumber") val phone:String,
    @SerializedName("trainerPassword") val password:String
)
