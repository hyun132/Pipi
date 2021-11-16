package com.example.pipi.domain.repository

import com.example.pipi.data.response.LoginResponse

interface MemberRepository {
    suspend fun getMyMembers(id:String): LoginResponse
    suspend fun getMemberRequest(id:String): LoginResponse
}