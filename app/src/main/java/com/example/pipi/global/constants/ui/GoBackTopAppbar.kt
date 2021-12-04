package com.example.pipi.global.constants.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BLACK
import com.example.pipi.global.constants.ui.Colors.SURFACE
import timber.log.Timber

@Composable
fun DrawGoBackTopAppbar(isNavIconVisible: Boolean) {
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
                    onClick = { Timber.d("goBackClicked!") },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "go Back",
                            tint = PRIMARY_BLACK
                        )
                    },
                )
            } else null
        },
        elevation = 0.dp, backgroundColor = SURFACE,
    )
}
