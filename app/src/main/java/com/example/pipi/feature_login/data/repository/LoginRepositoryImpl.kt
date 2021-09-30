package com.example.pipi.feature_login.data.repository

import com.example.pipi.feature_login.data.data_source.LoginDao
import com.example.pipi.feature_login.data.data_source.remote.PipiApi
import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.repository.LogInRepository

class LoginRepositoryImpl(private val api: PipiApi) : LogInRepository {
    override suspend fun login(id: String, password: String) =
        api.logIn(id = id, password = password)


    override suspend fun autoLogin(token: String):LoginResponse {
        TODO("Not yet implemented")
    }

}