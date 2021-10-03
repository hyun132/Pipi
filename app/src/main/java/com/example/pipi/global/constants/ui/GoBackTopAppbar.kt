package com.example.pipi.global.constants.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.BLACK
import com.example.pipi.global.constants.ui.Colors.SURFACE

@Composable
fun drawGoBackTopAppbar(isNavIconVisible: Boolean) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_small_logo),
                contentDescription = "small logo",
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = 76.dp)
                    .fillMaxWidth()
            )
        },
        navigationIcon = { //navigationIcon이 뒤로가기, actions는 액션메뉴
            if (isNavIconVisible) {
                IconButton(
                    onClick = { Log.d("TAG", "goBackClicked!") },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "go Back",
                            tint = BLACK
                        )
                    },
                )
            } else null
        },
        elevation = 0.dp, backgroundColor = SURFACE,
    )
}
