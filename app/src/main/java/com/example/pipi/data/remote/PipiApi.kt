package com.example.pipi.data.remote

import com.example.pipi.domain.model.schedule.ScheduleResponse
import com.example.pipi.domain.model.login.LoginResponse
import com.example.pipi.domain.model.phoneauth.PhoneAuthResponse
import com.example.pipi.domain.model.ResetPasswordResponse
import com.example.pipi.domain.model.signup.SignUpResponse
import com.example.pipi.domain.model.login.LoginDto
import com.example.pipi.domain.model.signup.SignUpDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PipiApi {

    @GET("auth/sms/message")
    suspend fun requestAuthMessage(): PhoneAuthResponse

    @POST("auth/sms/compare")
    suspend fun checkAuthMessage(
        @Body key: Int
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
    ): ResetPasswordResponse

    suspend fun getMonthlySchedule(
        @Body phoneNumber:String,
        @Body isTrainer:Boolean,
        @Body yearMonth:String
    ): ScheduleResponse

    // update, create
    suspend fun saveDaySchedule(scheduleData: ScheduleResponse):ScheduleResponse
}