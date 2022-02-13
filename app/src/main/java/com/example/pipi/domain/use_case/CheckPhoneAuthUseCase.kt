package com.example.pipi.domain.use_case

import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.domain.model.phoneauth.PhoneAuthResponse
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class CheckPhoneAuthUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<CheckPhoneAuthUseCase.Params, PhoneAuthResponse>(Dispatchers.IO) {
    class Params(val key: Int)

    override suspend fun execute(parameters: Params) =
        repository.checkPhoneAuth(parameters.key)
}