package com.example.pipi.feature_login.presentation.main

import androidx.compose.runtime.Composable

sealed class TabItems(var title:String,var screen:@Composable ()->Unit ){
    object Members : TabItems("회원관리",{ MemebersScreen()})
    object MemberRequest: TabItems("친구요청", { MemeberRequestScreen() })
}
