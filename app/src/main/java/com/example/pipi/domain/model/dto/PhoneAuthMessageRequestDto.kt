package com.example.pipi.domain.model.dto

import com.google.gson.annotations.SerializedName

data class PhoneAuthMessageRequestDto(
    @SerializedName("phone") val phone : String,
    @SerializedName("isTrainer") val isTrainer : Boolean,
    @SerializedName("key") val key:Int
)
