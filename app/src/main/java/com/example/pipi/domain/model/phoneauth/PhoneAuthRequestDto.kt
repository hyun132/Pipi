package com.example.pipi.domain.model.phoneauth

import com.google.gson.annotations.SerializedName

data class PhoneAuthRequestDto(
    @SerializedName("phone") val phone:String,
    @SerializedName("isTrainer") val isTrainer:Boolean
)
