package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.repository.LogInRepository
import kotlinx.coroutines.Dispatchers

class LogInUseCase(private val repository: LogInRepository) :
    CoroutineUseCase<LogInUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String, val password: String)

    override suspend fun execute(parameters: Params) =
        repository.login(parameters.id, parameters.password)

}