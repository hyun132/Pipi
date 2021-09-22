package com.example.pipi.feature_login.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {

    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

}