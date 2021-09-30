package com.example.pipi.feature_login.presentation.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class SignupViewModel {

    val password = mutableStateOf<String>("")

    val phoneNumber = mutableStateOf<String>("")

    val nickName = mutableStateOf<String>("")

}