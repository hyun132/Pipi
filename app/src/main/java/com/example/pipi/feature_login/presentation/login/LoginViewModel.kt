package com.example.pipi.feature_login.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {

    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    val autoLogin =  mutableStateOf(false)
    val rememberPhoneNumber = mutableStateOf(false)

    fun login(id:String,password:String){

    }

}