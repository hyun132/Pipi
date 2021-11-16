package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.domain.model.dto.PhoneAuthMessageRequestDto
import com.example.pipi.domain.model.dto.PhoneAuthRequestDto
import com.example.pipi.domain.model.dto.SignUpDto
import com.example.pipi.domain.repository.SignUpRepository

class SignUpRepositoryImpl(private val api: PipiApi) : SignUpRepository {
    override suspend fun signUp(id: String, password: String, name: String) =
        api.signUp(signUpDto = SignUpDto(id, password, name))

    override suspend fun requestPhoneAuthMessage(
        phone: String,
        isTrainer: Boolean
    ) = api.requestAuthMessage(PhoneAuthRequestDto(phone, isTrainer))


    override suspend fun checkPhoneAuth(
        phone: String,
        isTrainer: Boolean,
        key: Int
    ) = api.checkAuthMessage(phoneAuthMessageRequestDto = PhoneAuthMessageRequestDto(phone, isTrainer, key))


}