package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.model.PhoneAuthResponse
import com.example.pipi.feature_login.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class RequestPhoneAuthMessageUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<RequestPhoneAuthMessageUseCase.Params, PhoneAuthResponse>(Dispatchers.IO) {
    data class Params(val phone: String, val isTrainer: Boolean)

    override suspend fun execute(parameters: Params):PhoneAuthResponse =
        repository.requestPhoneAuthMessage(parameters.phone, parameters.isTrainer)
}