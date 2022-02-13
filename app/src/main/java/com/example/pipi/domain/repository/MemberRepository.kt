package com.example.pipi.domain.repository

import com.example.pipi.domain.model.login.LoginResponse

/**
 * TODO : api 나오면 리턴타입 변경해야함.
 */
interface MemberRepository {
    suspend fun getMyMembers(id:String): LoginResponse
    suspend fun getMemberRequest(id:String): LoginResponse
    suspend fun approveMemberRequest(id:String): LoginResponse
    suspend fun denyMemberRequest(id:String): LoginResponse
}