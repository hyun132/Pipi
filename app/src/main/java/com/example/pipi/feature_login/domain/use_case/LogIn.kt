package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.domain.repository.LogInRepository

class LogIn(private val repository: LogInRepository) {
    suspend operator fun invoke(id:String,password:String){
        try {
            repository.login(id,password)
        }catch (e:Exception){

        }

    }
}