package com.example.pipi.feature_login.presentation.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    val phoneNumber = MutableLiveData<String>("")

    val authNumber = MutableLiveData<String>("")

    fun checkAuthSuccess(): Boolean {
        // 서버에 요청한 결과가 success일 경우
        return true
    }

    val nickName = MutableLiveData<String>("")
    fun checkNickNameValid(): Boolean {
        // 서버에 요청한 결과가 success일 경우
        return true
    }

    val password = MutableLiveData<String>("")

    val confirmPassword = MutableLiveData<String>("")

    val showDialog = mutableStateOf<Boolean>(false)
    val dialogMessage = MutableLiveData<String>("")

    fun checkPasswordValid(): Boolean {
        return password.value?.let { !(it.length < 6 || it.length > 12) } ?: false
    }

    fun checkConfirmPassword(): Boolean {
        return confirmPassword.value?.let { it == password.value } ?: false
    }
}

