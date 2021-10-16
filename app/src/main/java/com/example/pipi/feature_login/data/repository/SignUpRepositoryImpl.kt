package com.example.pipi.feature_login.data.repository

import com.example.pipi.feature_login.data.data_source.SignUpDao
import com.example.pipi.feature_login.data.data_source.remote.PipiApi
import com.example.pipi.feature_login.domain.model.SignUpResponse
import com.example.pipi.feature_login.domain.repository.SignUpRepository

class SignUpRepositoryImpl(private val api: PipiApi) : SignUpRepository {
    override suspend fun signUp(id: String, password: String, name: String) =
        api.signUp(id = id, password = password, name = name)

    override suspend fun requestPhoneAuthMessage(
        phone: String,
        isTrainer: Boolean
    ) = api.requestAuthMessage(phone, isTrainer)


    override suspend fun checkPhoneAuth(
        phone: String,
        isTrainer: Boolean,
        key: Int
    ) = api.checkAuthMessage(phone, isTrainer, key)


}