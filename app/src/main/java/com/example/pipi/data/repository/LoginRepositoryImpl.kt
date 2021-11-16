package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.model.dto.LoginDto
import com.example.pipi.domain.repository.LogInRepository

class LoginRepositoryImpl(private val api: PipiApi) : LogInRepository {
    override suspend fun login(id: String, password: String) =
        api.logIn(LoginDto(id,password))


    override suspend fun autoLogin(token: String): LoginResponse {
        TODO("Not yet implemented")
    }

}