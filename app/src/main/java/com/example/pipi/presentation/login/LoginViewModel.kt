package com.example.pipi.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.Pipi
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.use_case.AutoLogInUseCase
import com.example.pipi.domain.use_case.LogInUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * TODO : LiveData 걷어내고 xxxFlow로 변경할 것
 * repository type LoginRespositoryImpl 로 설정하면 안되는 이유
 */
class LoginViewModel(val logInUseCase: LogInUseCase, val autoLogInUseCase: AutoLogInUseCase) :
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
                                Pipi.prefs.token
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

    /**
     * TODO : 자동로그인 프로우 픽스되면 반영하기
     */
    fun autoLogin() {
        viewModelScope.launch {
            Pipi.prefs.token.let { token ->
                    autoLogInUseCase(AutoLogInUseCase.Params()).onEach { result ->
                        when (result) {
                            is Result.Success -> {
                                isLoading.value = false
                                isLoginSuccess.value = true
                                Pipi.prefs.token
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