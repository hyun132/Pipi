package com.example.pipi.feature_login.domain.model.dto

import com.google.gson.annotations.SerializedName

data class PhoneAuthRequestDto(
    @SerializedName("phone") val phone:String,
    @SerializedName("isTrainer") val isTrainer:Boolean
)
