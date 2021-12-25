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
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.*

class SignupViewModel(
    val requestPhoneAuthMessageUseCase: RequestPhoneAuthMessageUseCase,
    val checkPhoneAuthUseCase: CheckPhoneAuthUseCase,
    val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    val phoneNumber = MutableLiveData<String>("")
    fun setPhoneNumber(phone: String) {
        phoneNumber.value = phone
    }

    var time = 180
    var timerTask: Timer? = null
    val authNumber = MutableLiveData<String>("")
    fun setAuthNumber(number: String) {
        authNumber.value = number
    }

    val signUpSuccess = MutableLiveData<Boolean>(false)
    val formattedTime = MutableLiveData<String>("")
    val timerStarted = MutableLiveData(false)
    private val _phoneAuthSuccess = MutableLiveData(false)
    val phoneAuthSuccess: LiveData<Boolean> = _phoneAuthSuccess

    fun checkAuthSuccess() {
        // 서버에 요청한 결과가 success일 경우
        viewModelScope.launch {
            authNumber.value.let {
                checkPhoneAuthUseCase(
                    CheckPhoneAuthUseCase.Params(
                        phoneNumber.value!!,
                        true,
                        authNumber.value!!.toInt()
                    )
                ).onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            _phoneAuthSuccess.postValue(true)
                            timerTask?.cancel()
                        }
                        is Result.Error -> setDialogMessage("에러가 발생하였습니다. 다시 시도해 주세요")
                        is Result.Loading -> {

                        }
                    }
                }
            }
        }
    }

    fun requestSendAuthMessage() {
        timerStarted.value = false
        viewModelScope.launch {
            phoneNumber.value?.let {
                requestPhoneAuthMessageUseCase(
                    RequestPhoneAuthMessageUseCase.Params(
                        it,
                        true
                    )
                ).onEach { result ->
                    timerStarted.value = true
                    when (result) {
                        is Result.Success -> {
                            if (result.data?.success == true) {
                                setDialogMessage("인증번호를 문자로 전송하였습니다.")
                                Timber.d("success")
                            }
                        }
                        is Result.Error -> {
                            setDialogMessage("에러가 발생하였습니다.")
                            Timber.d("fail")
                        }
                        is Result.Loading -> {
                        }
                    }
                }
            }
        }
    }

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

    fun countTime() {
        timerTask?.cancel()
        if (timerStarted.value == true) {
            timerTask = kotlin.concurrent.timer(period = 1000) {
                formattedTime.postValue("${time / 60}:${time % 60}")
                time -= 1
                Timber.d(time.toString())
                if (time <= 0) timerTask?.cancel()
            }
        } else timerTask?.cancel()
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
            }
        }
    }
}

