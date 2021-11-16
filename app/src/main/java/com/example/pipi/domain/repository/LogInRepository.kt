package com.example.pipi.domain.repository

import com.example.pipi.data.response.LoginResponse

interface LogInRepository {
    suspend fun login(id:String,password:String): LoginResponse
    suspend fun autoLogin(token:String): LoginResponse
}