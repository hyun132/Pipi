package com.example.pipi.presentation.main.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.domain.use_case.GetMyMembersUseCase
import com.example.pipi.global.constants.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MemberViewModel(private val getMyMembersUseCase: GetMyMembersUseCase) : ViewModel() {

    var memberState by mutableStateOf(MemberState())
        private set

    private val _uiEnvent = Channel<UiEvent>()
    val uiEvnet = _uiEnvent.receiveAsFlow()

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    //내 정보 가지고 있을 객체 필요
    fun getMembers() {
        viewModelScope.launch {
            getMyMembersUseCase(GetMyMembersUseCase.Params("123123"))
        }
    }

    fun setQuery(query: String) {
        Timber.i("set query: $query")
        memberState = memberState.copy(query = query)
    }

    /**
     * 입력된 키워드가 포함된 멤버 출력해주는 함수.
     */
    fun searchMemberByName() {
        val reg = (".*" + memberState.query.toList().joinToString(".*")).toRegex()
        memberState = memberState.copy(
            filteredMemberList = if (memberState.query.isNotEmpty() && memberState.query.isNotBlank()) {
                memberState.memberList.filter { member: Member ->
                    member.nickname.matches(reg)
                }
            } else memberState.memberList
        )
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

    /**
     * TODO : 오프라인시에도 친구목록 확인하는 경우가 있으므로 db에 저장해 둘 필요 있음. 인터넷 연결이 되어있지 않을 시 db에서 불러오도록 하는 로직필요.
     * TODO : 인터넷연결될 경우 친구 목록을 불러오고 백그라운드에서 db에 저장된 친구목록 갱신해주는 로직이 필요.
     * TODO : 이 때 친구 목록에 변화가 생긴 경우 그 친구 맨 위로 끌어올려서 새로운 멤버임을 알림.
     * 멤버는 서버에서 한번 불러오면 데이터베이스에 캐싱해둘것.
     * 멤버리스트를 서버와 동기화하는 시점은 논의해볼 것.
     */
    fun getMyMembers() {
        viewModelScope.launch(Dispatchers.IO) {
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
            memberState =
                memberState.copy(memberList = dummyMemebers, filteredMemberList = dummyMemebers)
        }
        Timber.d("loadMemberCalled>>>>>>>>")
    }
}