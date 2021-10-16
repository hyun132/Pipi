package com.example.pipi.feature_login.presentation.signup

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.global.result.Result
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class SignupViewModel(
    val requestPhoneAuthMessageUseCase: RequestPhoneAuthMessageUseCase
) : ViewModel() {

    val phoneNumber = MutableLiveData<String>("")

    val authNumber = MutableLiveData<String>("")
    val formattedTime = MutableLiveData<String>("")
    val timerStarted = MutableLiveData(false)

    fun checkAuthSuccess(): Boolean {
        // 서버에 요청한 결과가 success일 경우
        return true
    }

    fun requestSendAuthMessage() {
        timerStarted.value = false
        viewModelScope.launch {
            phoneNumber.value?.let {
                val result = requestPhoneAuthMessageUseCase(
                    RequestPhoneAuthMessageUseCase.Params(
                        it,
                        true
                    )
                )
                timerStarted.value = true
                when (result) {
                    is Result.Success -> {
                        if (result.data.success) {
                            setDialogMessage("인증번호를 문자로 전송하였습니다.")
                            Timber.d("success")
                        }
                    }
                    is Result.Error -> {
                        setDialogMessage("에러가 발생하였습니다.")
                        Timber.d("fail")
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
        var time = 180
        var timerTask: Timer? = null
        timerTask?.cancel()
        if (timerStarted.value == true) {
            timerTask = kotlin.concurrent.timer(period = 1000) {
                time -= 1
                val min = time / 60
                val sec = time % 60
                formattedTime.postValue("$min:$sec")
            }

            if (time <= 0) timerTask.cancel()
        } else timerTask?.cancel()
    }
}

