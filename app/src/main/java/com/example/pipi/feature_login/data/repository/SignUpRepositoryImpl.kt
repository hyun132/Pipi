package com.example.pipi.feature_login.data.repository

import com.example.pipi.feature_login.data.data_source.SignUpDao
import com.example.pipi.feature_login.data.data_source.remote.PipiApi
import com.example.pipi.feature_login.domain.model.SignUpResponse
import com.example.pipi.feature_login.domain.model.dto.PhoneAuthMessageRequestDto
import com.example.pipi.feature_login.domain.model.dto.PhoneAuthRequestDto
import com.example.pipi.feature_login.domain.model.dto.SignUpDto
import com.example.pipi.feature_login.domain.repository.SignUpRepository

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