package com.example.pipi.domain.use_case

import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.domain.model.phoneauth.PhoneAuthResponse
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class RequestPhoneAuthMessageUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<RequestPhoneAuthMessageUseCase.Params, PhoneAuthResponse>(Dispatchers.IO) {
    class Params()

    override suspend fun execute(parameters: Params): PhoneAuthResponse =
        repository.requestPhoneAuthMessage()
}