package com.example.pipi.domain.use_case

import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.data.response.SignUpResponse
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class SignUpUseCase(private val repository: SignUpRepositoryImpl) :
    CoroutineUseCase<SignUpUseCase.Params, SignUpResponse>(Dispatchers.IO) {
    data class Params(val id: String, val password: String, val name: String)

    override suspend fun execute(parameters: Params): SignUpResponse =
        repository.signUp(parameters.id, parameters.password, parameters.name)

}