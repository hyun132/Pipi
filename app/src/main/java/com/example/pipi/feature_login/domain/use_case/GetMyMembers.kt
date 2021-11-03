package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.repository.LogInRepository
import com.example.pipi.feature_login.domain.repository.MemberRepository
import com.example.pipi.feature_login.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class GetMyMembers(private val repository: MemberRepository) :
    CoroutineUseCase<GetMyMembers.Params, LoginResponse>(Dispatchers.IO) {
    class Params(val id: String)

    override suspend fun execute(parameters: Params) =
        repository.getMyMembers(parameters.id)
}