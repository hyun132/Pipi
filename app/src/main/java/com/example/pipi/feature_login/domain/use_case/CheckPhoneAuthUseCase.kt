package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.model.PhoneAuthResponse
import com.example.pipi.feature_login.domain.repository.LogInRepository
import kotlinx.coroutines.Dispatchers

class CheckPhoneAuthUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<CheckPhoneAuthUseCase.Params, PhoneAuthResponse>(Dispatchers.IO) {
    class Params(val phone: String, val isTrainer: Boolean, val key: Int)

    override suspend fun execute(parameters: Params) =
        repository.checkPhoneAuth(parameters.phone, parameters.isTrainer, parameters.key)
}