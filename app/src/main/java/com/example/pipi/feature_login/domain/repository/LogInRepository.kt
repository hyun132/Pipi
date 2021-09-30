package com.example.pipi.feature_login.domain.repository

import com.example.pipi.feature_login.domain.model.LoginResponse

interface LogInRepository {
    suspend fun login(id:String,password:String): LoginResponse
    suspend fun autoLogin(token:String):LoginResponse
}