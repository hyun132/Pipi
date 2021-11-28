package com.example.pipi.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.data.repository.LoginRepositoryImpl
import kotlinx.coroutines.launch

/**
 * TODO : LiveData 걷어내고 xxxFlow로 변경할 것
 */
class LoginViewModel(private val repository: LoginRepositoryImpl) : ViewModel() {

    val id = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    val autoLogin = mutableStateOf(false)
    val rememberPhoneNumber = mutableStateOf(false)

    val isLoading = MutableLiveData(false)
    val isLoginSuccess = MutableLiveData<Boolean>(false)

    fun login() {
        viewModelScope.launch {
            isLoading.value = true
            id.value?.let { id ->
                password.value?.let { pw ->
                    isLoginSuccess.postValue(
                        repository.login(
                            id,
                            pw
                        ).success
                    )
                }
            }
            isLoading.value = false
        }
    }
}