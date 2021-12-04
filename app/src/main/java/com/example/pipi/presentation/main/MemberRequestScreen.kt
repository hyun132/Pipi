package com.example.pipi.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BLACK
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar

@ExperimentalMaterialApi
@Composable
fun MemberRequestScreen(viewModel: MainViewModel, goBack: () -> Unit) {
    val requests = viewModel.memberRequest
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DrawTopAppBar(goBack)
        DrawContent(requests)
    }
}

@Composable
fun DrawContent(data: MutableState<List<Member>>) {
    if (data.value.isNotEmpty()) {
        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {
            itemsIndexed(data.value) { index, item ->
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    DrawMemberItem(item) {/* TODO : onClick() */ }
                }
            }
        }
    } else {
        /**
         * TODO : 스켈레톤 이미지 나오면 붙여주기
         */
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "요청된 회원이 없네요.")
            Text(text = "회원님께 요청방법을 알려주세요!")
        }
    }
}

@Composable
fun DrawMemberItem(item: Member, onClick: () -> Unit?) {
    MemberItem(member = item, onClick = { onClick() }) {
        Row(horizontalArrangement = Arrangement.End) {
            Button(
                onClick = { /*수락*/ },
                modifier = Modifier
                    .height(25.dp)
                    .width(66.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "회원수락",
                    color = Color.White,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(
                onClick = { /*거절*/ },
                border = BorderStroke(1.dp, PRIMARY_BLACK),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(25.dp)
                    .width(66.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(Color.White),
            ) {
                Text(
                    text = "거절",
                    color = PRIMARY_BLACK,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun DrawTopAppBar(goBack: () -> Unit) {
    DefaultTopAppbar(title = {
        Text(
            text = "회원등록",
            style = MaterialTheme.typography.h1,
            color = PRIMARY_BLACK,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }, navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
            contentDescription = "닫기",
            Modifier
                .size(12.dp)
                .clickable { goBack() }
        )
    })
}
