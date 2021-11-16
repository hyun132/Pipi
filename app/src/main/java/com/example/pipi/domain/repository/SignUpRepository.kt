package com.example.pipi.domain.repository

import com.example.pipi.data.response.PhoneAuthResponse
import com.example.pipi.data.response.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(id: String, password: String, name: String): SignUpResponse
    suspend fun requestPhoneAuthMessage(phone: String, isTrainer: Boolean): PhoneAuthResponse
    suspend fun checkPhoneAuth(phone: String, isTrainer: Boolean, key: Int): PhoneAuthResponse
}