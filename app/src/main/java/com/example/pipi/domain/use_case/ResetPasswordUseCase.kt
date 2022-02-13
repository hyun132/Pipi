package com.example.pipi.domain.use_case

import com.example.pipi.domain.model.ResetPasswordResponse
import com.example.pipi.domain.repository.UserInfoRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class ResetPasswordUseCase(private val repository: UserInfoRepository) :
    CoroutineUseCase<ResetPasswordUseCase.Params, ResetPasswordResponse>(Dispatchers.IO) {
    class Params(val id: String, val password: String)

    override suspend fun execute(parameters: Params) =
        repository.resetPassword(parameters.id, parameters.password)
}