package com.example.pipi.feature_login.presentation.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel:ViewModel() {

    val password = mutableStateOf<String>("")

    val phoneNumber = mutableStateOf<String>("")

    val authNumber = mutableStateOf<String>("")

    val nickName = mutableStateOf<String>("")

    val isPhoneNumberAuthSuccessed = MutableLiveData<Boolean>(false)

}