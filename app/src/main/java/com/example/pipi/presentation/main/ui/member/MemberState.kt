package com.example.pipi.presentation.main.ui.member

data class MemberState(
    val query: String = "",
    val isSearching: Boolean = false,
    val memberList: List<Member> = emptyList()
)