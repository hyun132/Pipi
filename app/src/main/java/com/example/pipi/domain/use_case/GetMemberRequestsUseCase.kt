package com.example.pipi.domain.use_case

import com.example.pipi.domain.model.login.LoginResponse
import com.example.pipi.domain.repository.MemberRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class GetMemberRequestsUseCase(private val repository: MemberRepository) :
    CoroutineUseCase<GetMemberRequestsUseCase.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String)

    override suspend fun execute(parameters: Params) =
        repository.getMemberRequest(parameters.id)
}