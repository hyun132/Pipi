package com.example.pipi.domain.repository

import com.example.pipi.domain.model.login.LoginResponse

interface LogInRepository {
    suspend fun login(id: String, password: String): LoginResponse
    suspend fun autoLogin(): LoginResponse
}