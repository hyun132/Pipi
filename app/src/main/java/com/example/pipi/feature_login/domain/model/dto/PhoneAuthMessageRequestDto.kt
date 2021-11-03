package com.example.pipi.feature_login.domain.model.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class PhoneAuthMessageRequestDto(
    @SerializedName("phone") val phone : String,
    @SerializedName("isTrainer") val isTrainer : Boolean,
    @SerializedName("key") val key:Int
)
