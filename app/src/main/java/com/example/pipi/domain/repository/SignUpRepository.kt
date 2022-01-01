package com.example.pipi.domain.repository

import com.example.pipi.data.response.PhoneAuthResponse
import com.example.pipi.data.response.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(id: String, password: String, name: String): SignUpResponse
    suspend fun requestPhoneAuthMessage(): PhoneAuthResponse
    suspend fun checkPhoneAuth(key: Int): PhoneAuthResponse
}