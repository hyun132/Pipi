package com.example.pipi.domain.repository

import com.example.pipi.domain.model.ResetPasswordResponse

interface UserInfoRepository {
    suspend fun resetPassword(id: String, password: String): ResetPasswordResponse
//    suspend fun getUserSchedule(id: String, password: String): LoginResponse
}