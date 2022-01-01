package com.example.pipi.presentation.duplicated

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.use_case.CheckPhoneAuthUseCase
import com.example.pipi.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class PhoneAuthViewModel(
    val requestPhoneAuthMessageUseCase: RequestPhoneAuthMessageUseCase,
    val checkPhoneAuthUseCase: CheckPhoneAuthUseCase,
) : ViewModel() {

    val phoneNumber = mutableStateOf<String>("")
    val errorMessage = mutableStateOf("")
    val formattedTime = MutableLiveData<String>("")
    val timerStarted = mutableStateOf(false)
    private val _phoneAuthSuccess = MutableLiveData(false)
    val phoneAuthSuccess: LiveData<Boolean> = _phoneAuthSuccess
    var time = 180
    var timerTask: Timer? = null
    val authNumber = MutableLiveData<String>("")

    fun setPhoneNumber(phone: String) {
        phoneNumber.value = phone
    }

    fun setAuthNumber(number: String) {
        authNumber.value = number
    }

    fun checkAuthSuccess() {
        viewModelScope.launch {
            authNumber.value.let {
                checkPhoneAuthUseCase(
                    CheckPhoneAuthUseCase.Params(
                        authNumber.value!!.toInt()
                    )
                ).onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            errorMessage.value = ""
                            stopCount();
                            _phoneAuthSuccess.postValue(true)
                        }
                        is Result.Error -> {
                            errorMessage.value = "에러가 발생하였습니다. 다시 시도해 주세요"
                        }
                        is Result.Loading -> {
                            errorMessage.value = ""
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    fun requestSendAuthMessage() {
        if (timerStarted.value) timerStarted.value = false
        viewModelScope.launch {
            phoneNumber.value.let {
                requestPhoneAuthMessageUseCase(
                    RequestPhoneAuthMessageUseCase.Params()
                ).onEach { result ->
                    when (result) {
                        is Result.Success -> {
                            timerStarted.value = true
                            if (result.data?.success == true) {
                                errorMessage.value = "인증번호를 문자로 전송하였습니다."
                                Timber.d("success")
                            }
                        }
                        is Result.Error -> {
                            errorMessage.value = result.message.toString()
                            Timber.d("fail")
                            stopCount();
                        }
                        is Result.Loading -> {
                        }
                    }
                }.launchIn(this)
            }
        }
    }

    fun countTime() {
        stopCount()
        if (timerStarted.value) {
            timerTask = kotlin.concurrent.timer(period = 1000) {
                formattedTime.postValue("${time / 60} : ${time % 60}")
                time -= 1
                Timber.d(time.toString())
                if (time <= 0 || !timerStarted.value) stopCount()
            }
        }
    }

    fun stopCount() {
        Timber.d("stopCount!!")
        timerStarted.value = false
        timerTask?.cancel()
    }
}

