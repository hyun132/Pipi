package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.domain.model.signup.SignUpDto
import com.example.pipi.domain.repository.SignUpRepository

class SignUpRepositoryImpl(private val api: PipiApi) : SignUpRepository {
    override suspend fun signUp(id: String, password: String, name: String) =
        api.signUp(signUpDto = SignUpDto(id, password, name))

    override suspend fun requestPhoneAuthMessage() = api.requestAuthMessage()


    override suspend fun checkPhoneAuth(key: Int) =
        api.checkAuthMessage(key)


}