package com.example.pipi.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.Pipi
import com.example.pipi.domain.use_case.LogInUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * TODO : LiveData 걷어내고 xxxFlow로 변경할 것
 * repository type LoginRespositoryImpl 로 설정하면 안되는 이유
 * TODO : 여기도 state 묶어서 관리할 수 있도록 수정해야함.
 */
class LoginViewModel(val logInUseCase: LogInUseCase) :
    ViewModel() {

    val id = mutableStateOf<String>("")
    val password = mutableStateOf<String>("")

    val autoLogin = mutableStateOf(false)
    val rememberPhoneNumber = mutableStateOf(false)

    val isLoading = mutableStateOf(false)
    val isLoginSuccess = mutableStateOf<Boolean>(false)

    val errorMessage = mutableStateOf("")

    fun login() {
        viewModelScope.launch {
            id.value.let { id ->
                password.value.let { pw ->
                    logInUseCase(LogInUseCase.Params(id, pw)).onEach { result ->
                        when (result) {
                            is Result.Success -> {
                                isLoading.value = false
                                isLoginSuccess.value = true
                                saveAutoLoginState()
                            }
                            is Result.Error -> {
                                isLoading.value = false
                                errorMessage.value = result.message.toString()
                            }
                            is Result.Loading -> {
                                isLoading.value = true
                            }
                        }
                    }.launchIn(this)
                }
            }
        }
    }

    private fun saveAutoLoginState() {
        Pipi.prefs.tryAutoLogin = autoLogin.value
    }

}