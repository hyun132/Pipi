package com.example.pipi.feature_login.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MemebersScreen() {
    Text(text = "Memeber")
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "나의 회원")
            Text(text = "13 명")
            Text(text = "사용중")
        }
        //lazycolum
    }
}