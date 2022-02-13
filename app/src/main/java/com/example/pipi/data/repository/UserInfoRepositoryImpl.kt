package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.domain.model.ResetPasswordResponse
import com.example.pipi.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl(private val api: PipiApi) : UserInfoRepository {
    override suspend fun resetPassword(id: String, password: String): ResetPasswordResponse =
        api.changePassword(id, password)
}