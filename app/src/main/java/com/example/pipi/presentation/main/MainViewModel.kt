package com.example.pipi.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.data.repository.MemberRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MemberRepositoryImpl) : ViewModel() {

    //이 뷰모델 내 기능들 추후 bottomnavigation menu 바탕으로 viewmodel만들어서 나눌것.
    val isBottomSheetExpanded = MutableLiveData(false)
    fun setBottomSheetState(expandBottomSheet: Boolean) {
        isBottomSheetExpanded.postValue(expandBottomSheet)
    }

    private val _members: MutableState<List<Member>> = mutableStateOf(listOf())
    val members: MutableState<List<Member>> get() = _members

    private val _memberRequest: MutableState<List<Member>> = mutableStateOf(listOf())
    val memberRequest: MutableState<List<Member>> get() = _members

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    /**
     * TODO
     * 멤버 불러오는 부분도 stateFlow로 변경할 것.
     */
    fun getMyMembers() {
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
            _members.value = dummyMemebers
        }
    }

    private fun getMemberRequest() {
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
            _memberRequest.value = dummyMemebers
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