package com.example.pipi.domain.repository

import com.example.pipi.domain.model.phoneauth.PhoneAuthResponse
import com.example.pipi.domain.model.signup.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(id: String, password: String, name: String): SignUpResponse
    suspend fun requestPhoneAuthMessage(): PhoneAuthResponse
    suspend fun checkPhoneAuth(key: Int): PhoneAuthResponse
}