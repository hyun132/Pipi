package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.model.dto.LoginDto
import com.example.pipi.domain.repository.LogInRepository

class LoginRepositoryImpl(private val api: PipiApi) : LogInRepository {
    override suspend fun login(id: String, password: String) = api.logIn(LoginDto(id, password))

//    override suspend fun login(id: String, password: String) = flow {
//        emit(Result.Loading())
//        var data: LoginResponse? = null
//        try {
//            data = api.logIn(LoginDto(id, password))
//        } catch (e: HttpException) {
//            emit(Result.Error(message = "Http Exception", data = data))
//        } catch (e: IOException) {
//            emit(Result.Error(message = "IOException, check your internet connection", data = data))
//        }
//        emit(Result.Success(data))
//    }

    override suspend fun autoLogin() = api.autoLogIn()

}