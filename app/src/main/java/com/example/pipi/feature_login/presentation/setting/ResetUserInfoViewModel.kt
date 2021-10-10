package com.example.pipi.feature_login.presentation.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResetUserInfoViewModel:ViewModel() {

    val password = MutableLiveData<String>("")
    val confirmPassword = MutableLiveData<String>("")

    fun checkPasswordValid(): Boolean {
        return password.value?.let { !(it.length < 6 || it.length > 12) } ?: false
    }

    fun checkConfirmPassword(): Boolean {
        return confirmPassword.value == password.value
    }
}