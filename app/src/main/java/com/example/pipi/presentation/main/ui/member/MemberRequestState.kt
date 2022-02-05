package com.example.pipi.presentation.main.ui.member

data class MemberRequestState(
    val requestList: List<Member> = emptyList(),
    val isLoading:Boolean = false
)