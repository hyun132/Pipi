package com.example.pipi.domain.use_case

import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class AutoLogInUseCase(private val repository: LogInRepository) :
    CoroutineUseCase<AutoLogInUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params()

    override suspend fun execute(parameters: Params) =
        repository.autoLogin()
}