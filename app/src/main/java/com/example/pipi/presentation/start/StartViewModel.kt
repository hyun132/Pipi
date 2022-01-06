package com.example.pipi.presentation.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.Pipi
import com.example.pipi.domain.use_case.AutoLogInUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class StartViewModel(val autoLogInUseCase: AutoLogInUseCase) : ViewModel() {
    // TODO : 자동로그인 여기 추가, sharedPref자동로그인 여부 체크부분도 추가할것.
    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess : StateFlow<Boolean> = _isLoginSuccess
    val errorMessage = mutableStateOf("")
    val isLoading = MutableStateFlow(true)

    /**
     * TODO : 자동로그인 프로우 픽스되면 반영하기
     */
    fun autoLogin() {
        viewModelScope.launch {
            Pipi.prefs.token.let { token ->
                autoLogInUseCase(AutoLogInUseCase.Params()).onEach { result ->
                    Timber.d("autoLogin :: ",Thread.currentThread().name)
                    when (result) {
                        is Result.Success -> {
                            _isLoginSuccess.value = true
//                            isLoading.value = false
                        }
                        is Result.Error -> {
                            errorMessage.value = result.message.toString()
                            isLoading.value = false
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