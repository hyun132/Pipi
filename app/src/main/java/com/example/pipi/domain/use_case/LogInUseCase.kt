package com.example.pipi.domain.use_case

import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class LogInUseCase(private val repository: LogInRepository) :
    CoroutineUseCase<LogInUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String, val password: String)

    override suspend fun execute(parameters: Params) =
        repository.login(parameters.id, parameters.password)
}