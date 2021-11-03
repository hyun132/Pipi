package com.example.pipi.feature_login.data.data_source.remote

import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.model.PhoneAuthResponse
import com.example.pipi.feature_login.domain.model.SignUpResponse
import com.example.pipi.feature_login.domain.model.dto.LoginDto
import com.example.pipi.feature_login.domain.model.dto.PhoneAuthMessageRequestDto
import com.example.pipi.feature_login.domain.model.dto.PhoneAuthRequestDto
import com.example.pipi.feature_login.domain.model.dto.SignUpDto
import com.example.pipi.global.constants.Const.BASE_URL
import retrofit2.http.*

interface PipiApi {

    @POST("auth/sms/message")
    suspend fun requestAuthMessage(
        @Body phoneAuthRequestDto: PhoneAuthRequestDto,
    ):PhoneAuthResponse

    @POST("auth/sms/compare")
    suspend fun checkAuthMessage(
        @Body phoneAuthMessageRequestDto: PhoneAuthMessageRequestDto
    ):PhoneAuthResponse

    @POST("trainers/register")
    suspend fun signUp(
        @Body signUpDto: SignUpDto
    ):SignUpResponse

    @POST("trainers/login")
    suspend fun logIn(
        @Body loginDto: LoginDto,
    ):LoginResponse

    @GET("trainers/logout")
    suspend fun logOut(
        @Path("trainerPhonNumber") id : String,
    )

}