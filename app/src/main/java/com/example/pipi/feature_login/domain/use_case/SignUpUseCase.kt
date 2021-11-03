package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.model.SignUpResponse
import com.example.pipi.feature_login.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class SignUpUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<SignUpUseCase.Params, SignUpResponse>(Dispatchers.IO) {
    data class Params(val id: String, val password: String, val name: String)

    override suspend fun execute(parameters: Params): SignUpResponse =
        repository.signUp(parameters.id, parameters.password, parameters.name)

}