package com.example.pipi.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.presentation.main.ui.member.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    //이 뷰모델 내 기능들 추후 bottomnavigation menu 바탕으로 viewmodel만들어서 나눌것.
    val isBottomSheetExpanded = MutableLiveData(false)
    fun setBottomSheetState(expandBottomSheet: Boolean) {
        isBottomSheetExpanded.postValue(expandBottomSheet)
    }

    fun showBottomSheet() {
        isBottomSheetExpanded.postValue(true)
    }

    fun hideBottomSheet() {
        isBottomSheetExpanded.postValue(false)
    }
}