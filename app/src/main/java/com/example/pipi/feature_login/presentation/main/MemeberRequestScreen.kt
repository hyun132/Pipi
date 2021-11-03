package com.example.pipi.feature_login.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalMaterialApi
@Composable
fun MemeberRequestScreen() {
    Column(modifier = Modifier.background(Color.Yellow).fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "나의 회원")
            Text(text = "13 명")
            Text(text = "사용중")
        }
        //lazycolum
        Text(text = "MemeberRequest")
    }
}