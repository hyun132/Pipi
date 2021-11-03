package com.example.pipi.feature_login.data.repository

import com.example.pipi.feature_login.data.data_source.LoginDao
import com.example.pipi.feature_login.data.data_source.remote.PipiApi
import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.repository.LogInRepository
import com.example.pipi.feature_login.domain.repository.MemberRepository

class MemberRepositoryImpl(private val api: PipiApi) : MemberRepository {
    override suspend fun getMyMembers(id: String): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMemberRequest(id: String): LoginResponse {
        TODO("Not yet implemented")
    }

}