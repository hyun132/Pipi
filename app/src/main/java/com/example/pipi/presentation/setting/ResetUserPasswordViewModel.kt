package com.example.pipi.presentation.setting

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.use_case.ResetPasswordUseCase
import com.example.pipi.global.constants.utils.passwordValidation
import com.example.pipi.global.result.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class ResetUserPasswordViewModel(private val resetPasswordUseCase: ResetPasswordUseCase) :
    ViewModel() {

    /**
     * TODO : id 나중에 토큰으로 변경되면 수정하기
     */
    val id: String = "01092875815"

    val password = mutableStateOf<String>("")
    val confirmPassword = mutableStateOf<String>("")

    val errorMessage = mutableStateOf<String>("")

    val isResetPasswordSuccess = mutableStateOf(false)

    fun checkPasswordValid(): Boolean {
        return passwordValidation(password.value)
    }

    fun checkConfirmPassword(): Boolean {
        Timber.d("a: ",confirmPassword.value,"b:",password.value)
        return checkPasswordValid() && (confirmPassword.value == password.value)
    }

    fun requestSetNewPassword() {
        viewModelScope.launch {
            resetPasswordUseCase(ResetPasswordUseCase.Params(id, password.value)).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        errorMessage.value = "비밀번호 재설정을 성공하였습니다."
                        isResetPasswordSuccess.value = true
                    }
                    is Result.Error -> {
                        errorMessage.value = result.message.toString()
                    }
                    is Result.Loading -> {
                    }
                }
            }.launchIn(this)
        }
    }
}