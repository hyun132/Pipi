package com.example.pipi.presentation.main.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.use_case.ApproveMemberRequestUseCase
import com.example.pipi.domain.use_case.DenyMemberRequestUseCase
import com.example.pipi.domain.use_case.GetMemberRequestsUseCase
import com.example.pipi.global.constants.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MemberRequestViewModel(
    val getMemberRequestsUseCase: GetMemberRequestsUseCase,
    val approveMemberRequestUseCase: ApproveMemberRequestUseCase,
    val denyMemberRequestUseCase: DenyMemberRequestUseCase
) : ViewModel() {

    private val _uiEnvent = Channel<UiEvent>()
    val uiEvnet = _uiEnvent.receiveAsFlow()

    /**
     * TODO : 여기 아이템은 스와이프 벗겨내야함.
     */
    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    var memberState by mutableStateOf(MemberRequestState())
        private set

    fun approveRequest(member: Member) {
        //멤버가 없을경우 스낵바 띄워줌
        viewModelScope.launch {
            _uiEnvent.send(UiEvent.ShowSnackbar("탈퇴한 회원이거나 요청을 취소한 회원입니다."))
        }
    }

    fun denyRequest(member: Member) {
        viewModelScope.launch {

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

    fun getMemberRequest() {
//        viewModelScope.launch {
//            getMemberRequestsUseCase(GetMemberRequestsUseCase.Params(id)).onEach { result ->
//                when (result) {
//                    is Result.Success -> {
//                        memberState = memberState.copy(isLoading = false)
//                    }
//                    is Result.Error -> {
//                        memberState = memberState.copy(isLoading = false)
//                    }
//                    is Result.Loading -> {
//                        memberState = memberState.copy(isLoading = true)
//                    }
//                }
//            }
//        }
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
            memberState = memberState.copy(requestList = dummyMemebers)
        }
    }
}