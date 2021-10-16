package com.example.pipi.feature_login.data.data_source.remote

import com.example.pipi.feature_login.domain.model.LoginResponse
import com.example.pipi.feature_login.domain.model.PhoneAuthResponse
import com.example.pipi.feature_login.domain.model.SignUpResponse
import com.example.pipi.global.constants.Const.BASE_URL
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PipiApi {

    @POST("auth/sms/message")
    suspend fun requestAuthMessage(
        @Query("phone") phone : String,
        @Query("isTrainer") isTrainer : Boolean,
    ):PhoneAuthResponse

    @POST("auth/sms/compare")
    suspend fun checkAuthMessage(
        @Query("phone") phone : String,
        @Query("isTrainer") isTrainer : Boolean,
        @Query("key") key:Int
    ):PhoneAuthResponse

    @POST("trainers/register")
    suspend fun signUp(
        @Query("trainerPhonNumber") id : String,
        @Query("trainerPassword") password : String,
        @Query("trainerName") name:String
    ):SignUpResponse

    @POST("trainers/login")
    suspend fun logIn(
        @Query("trainerPhonNumber") id : String,
        @Query("trainerPassword") password : String,
    ):LoginResponse

    @POST("trainers/logout")
    suspend fun logOut(
        @Path("trainerPhonNumber") id : String,
    )

}