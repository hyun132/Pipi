package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.data.response.LoginResponse
import com.example.pipi.domain.repository.MemberRepository

class MemberRepositoryImpl(private val api: PipiApi) : MemberRepository {
    override suspend fun getMyMembers(id: String): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMemberRequest(id: String): LoginResponse {
        TODO("Not yet implemented")
    }

}