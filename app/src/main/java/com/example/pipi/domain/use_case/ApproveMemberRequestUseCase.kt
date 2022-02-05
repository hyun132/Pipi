package com.example.pipi.domain.use_case

import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.repository.MemberRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class ApproveMemberRequestUseCase(private val repository: MemberRepository) :
    CoroutineUseCase<ApproveMemberRequestUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String)

    override suspend fun execute(parameters: Params) =
        repository.approveMemberRequest(parameters.id)
}