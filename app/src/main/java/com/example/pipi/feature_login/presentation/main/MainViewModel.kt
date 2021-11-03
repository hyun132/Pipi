package com.example.pipi.feature_login.presentation.main

import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.feature_login.data.repository.LoginRepositoryImpl
import com.example.pipi.feature_login.data.repository.MemberRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MemberRepositoryImpl) : ViewModel() {

    init {
        getMyMembers()
    }

    val isBottomSheetExpanded = MutableLiveData(false)
    fun setBottomSheetState(expandBottomSheet: Boolean){
        isBottomSheetExpanded.postValue(expandBottomSheet)
    }

    private val _members = MutableStateFlow(listOf<Member>())
    val members: StateFlow<List<Member>> get() = _members

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    private fun getMyMembers() {
        viewModelScope.launch(Dispatchers.Default) {
            val dummyMemebers = listOf<Member>(
                Member("회원1"),
                Member("회원2"),
                Member("회원3"),
                Member("회원4"),
                Member("회원5"),
                Member("회원6"),
                Member("회원7"),
                Member("회원8"),
                Member("회원9"),
                Member("회원10"),
                Member("회원11"),
                Member("회원12"),
                Member("회원13"),
                Member("회원14"),
                Member("회원15"),
                Member("회원16"),
                Member("회원17"),
                Member("회원18"),
                Member("회원19"),
            )
            _members.emit(dummyMemebers)
        }
    }

    fun onItemExpanded(cardId: Int) {
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: Int) {
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }

    //내 정보 가지고 있을 객체 필요
    fun getMembers() {
        viewModelScope.launch {
            repository.getMyMembers(id = "123123")
        }
    }
}