package com.example.pipi.feature_login.domain.repository

import com.example.pipi.feature_login.domain.model.LoginResponse

interface MemberRepository {
    suspend fun getMyMembers(id:String): LoginResponse
    suspend fun getMemberRequest(id:String):LoginResponse
}