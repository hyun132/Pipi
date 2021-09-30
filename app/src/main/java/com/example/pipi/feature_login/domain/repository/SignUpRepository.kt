package com.example.pipi.feature_login.domain.repository

import com.example.pipi.feature_login.domain.model.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(id:String,password:String,name:String): SignUpResponse
}