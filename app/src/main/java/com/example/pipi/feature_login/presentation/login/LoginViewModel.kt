package com.example.pipi.feature_login.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.feature_login.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepositoryImpl) : ViewModel() {

    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    val autoLogin = mutableStateOf(false)
    val rememberPhoneNumber = mutableStateOf(false)

    fun login() {
        viewModelScope.launch {
            id.value?.let { id -> password.value?.let { pw -> repository.login(id, pw) } }
        }
    }
}