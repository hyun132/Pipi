package com.example.pipi.data.remote

import com.example.pipi.data.response.LoginResponse
import com.example.pipi.data.response.PhoneAuthResponse
import com.example.pipi.data.response.ResetPasswordResponse
import com.example.pipi.data.response.SignUpResponse
import com.example.pipi.domain.model.dto.LoginDto
import com.example.pipi.domain.model.dto.PhoneAuthMessageRequestDto
import com.example.pipi.domain.model.dto.PhoneAuthRequestDto
import com.example.pipi.domain.model.dto.SignUpDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PipiApi {

    @POST("auth/sms/message")
    suspend fun requestAuthMessage(
        @Body phoneAuthRequestDto: PhoneAuthRequestDto,
    ): PhoneAuthResponse

    @POST("auth/sms/compare")
    suspend fun checkAuthMessage(
        @Body phoneAuthMessageRequestDto: PhoneAuthMessageRequestDto
    ): PhoneAuthResponse

    @POST("trainers/register")
    suspend fun signUp(
        @Body signUpDto: SignUpDto
    ): SignUpResponse

    @POST("trainers/login")
    suspend fun logIn(
        @Body loginDto: LoginDto,
    ): LoginResponse

    @POST("trainers/login")
    suspend fun autoLogIn(): LoginResponse

    @GET("trainers/logout")
    suspend fun logOut(
        @Path("trainerPhonNumber") id: String,
    )

    @GET("trainers/resetPassword")
    suspend fun changePassword(
        @Body trainerPhoneNumber: String,
        @Body trainerPassword: String
    ):ResetPasswordResponse

}