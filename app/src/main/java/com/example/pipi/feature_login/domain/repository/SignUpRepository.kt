package com.example.pipi.feature_login.domain.repository

import com.example.pipi.feature_login.domain.model.PhoneAuthResponse
import com.example.pipi.feature_login.domain.model.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(id: String, password: String, name: String): SignUpResponse
    suspend fun requestPhoneAuthMessage(phone: String, isTrainer: Boolean): PhoneAuthResponse
    suspend fun checkPhoneAuth(phone: String, isTrainer: Boolean, key: Int): PhoneAuthResponse
}