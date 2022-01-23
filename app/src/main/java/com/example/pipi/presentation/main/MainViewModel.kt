package com.example.pipi.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pipi.data.repository.MemberRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val repository: MemberRepositoryImpl) : ViewModel() {

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

    private val _members: MutableState<List<Member>> = mutableStateOf(listOf())
    val members: MutableState<List<Member>> get() = _members

    private val _memberRequest: MutableState<List<Member>> = mutableStateOf(listOf())
    val memberRequest: MutableState<List<Member>> get() = _members

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    val searchQuery = mutableStateOf<String>("")

    /**
     * TODO :멤버 불러오는 부분도 stateFlow로 변경할 것.
     * TODO : 오프라인시에도 친구목록 확인하는 경우가 있으므로 db에 저장해 둘 필요 있음. 인터넷 연결이 되어있지 않을 시 db에서 불러오도록 하는 로직필요.
     * TODO : 인터넷연결될 경우 친구 목록을 불러오고 백그라운드에서 db에 저장된 친구목록 갱신해주는 로직이 필요.
     * TODO : 이 때 친구 목록에 변화가 생긴 경우 그 친구 맨 위로 끌어올려서 새로운 멤버임을 알림.
     * 멤버는 서버에서 한번 불러오면 데이터베이스에 캐싱해둘것.
     * 멤버리스트를 서버와 동기화하는 시점은 논의해볼 것.
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
        Timber.d("loadMemberCalled>>>>>>>>")
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

    fun deleteMember(member: Member){

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

    /**
     * 입력된 키워드가 포함된 멤버 출력해주는 함수.
     */
    fun searchMemberByName(): MutableState<List<Member>> {
        return if (searchQuery.value.isNotEmpty() && searchQuery.value.isNotBlank()) {
            val reg = (".*" + searchQuery.value.toList().joinToString(".*")).toRegex()
            mutableStateOf(_members.value.filter { member: Member ->
                member.nickname.matches(reg)
            })
        } else members
    }

}