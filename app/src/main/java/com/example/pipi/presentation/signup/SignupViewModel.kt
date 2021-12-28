package com.example.pipi.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.use_case.CheckPhoneAuthUseCase
import com.example.pipi.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.domain.use_case.SignUpUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.*
import kotlin.coroutines.coroutineContext

class SignupViewModel(
    val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    /**
     * TODO : phone number은 auth 화면에서 argument로 넘겨받기
     */
    val phoneNumber = MutableLiveData<String>("")
    fun setPhoneNumber(phone: String) {
        phoneNumber.value = phone
    }

    val signUpSuccess = MutableLiveData<Boolean>(false)
    val formattedTime = MutableLiveData<String>("")
    val timerStarted = MutableLiveData(false)
    private val _phoneAuthSuccess = MutableLiveData(false)
    val phoneAuthSuccess: LiveData<Boolean> = _phoneAuthSuccess

    val nickName = MutableLiveData<String>("")
    fun checkNickNameValid(): Boolean {
        // 서버에 요청한 결과가 success일 경우
        return true
    }

    val password = MutableLiveData<String>("")

    val confirmPassword = MutableLiveData<String>("")

    val dialogMessage = MutableLiveData<String>("")
    fun setDialogMessage(message: String) {
        dialogMessage.value = message
        dialogMessage.postValue(message)
        CoroutineScope(Dispatchers.Default).launch {
            delay(2000)
            dialogMessage.postValue("")
        }
    }

    fun checkPasswordValid(): Boolean {
        return password.value?.let { !(it.length < 6 || it.length > 12) } ?: false
    }

    fun checkConfirmPassword(): Boolean {
        return confirmPassword.value?.let { it == password.value } ?: false
    }

    fun requestSignUp() {
        viewModelScope.launch {
            signUpUseCase(
                SignUpUseCase.Params(
                    id = phoneNumber.value!!,
                    password.value!!,
                    nickName.value!!
                )
            ).onEach { result ->
                when (result) {
                    is Result.Success -> signUpSuccess.postValue(true)
                    is Result.Error -> signUpSuccess.postValue(false)
                    is Result.Loading -> {

                    }
                }
            }.launchIn(this)
        }
    }
}

