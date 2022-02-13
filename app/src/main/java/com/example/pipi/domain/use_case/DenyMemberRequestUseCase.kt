package com.example.pipi.domain.use_case

import com.example.pipi.domain.model.login.LoginResponse
import com.example.pipi.domain.repository.MemberRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class DenyMemberRequestUseCase(private val repository: MemberRepository) :
    CoroutineUseCase<DenyMemberRequestUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String)

    override suspend fun execute(parameters: Params) =
        repository.denyMemberRequest(parameters.id)
}