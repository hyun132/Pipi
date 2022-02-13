package com.example.pipi.presentation.main.ui.member

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.UiEvent
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.viewModel

@ExperimentalMaterialApi
@Composable
fun MemberRequestScreen(scaffoldState: ScaffoldState, goBack: () -> Unit) {
    val viewModel: MemberRequestViewModel by viewModel()
    val state = viewModel.memberState
    LaunchedEffect(key1 = true) {
        viewModel.getMemberRequest()
        viewModel.uiEvnet.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DrawTopAppBar(goBack)
        DrawContent(viewModel, state)
    }
}

@Composable
fun DrawContent(viewModel: MemberRequestViewModel, state: MemberRequestState) {
    if (state.requestList.isNotEmpty()) {
        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {
            itemsIndexed(state.requestList) { index, item ->
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    DrawMemberItem(
                        item, viewModel::approveRequest,
                        viewModel::denyRequest
                    )
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
fun DrawMemberItem(item: Member, onAccept: (Member) -> Unit, onDeny: (Member) -> Unit) {
    MemberItem(member = item, onClick = {}) {
        Row(horizontalArrangement = Arrangement.End) {
            Button(
                onClick = { onAccept(item) },
                modifier = Modifier
                    .height(25.dp)
                    .width(66.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BRAND_SECOND),
            ) {
                Text(
                    text = "회원수락",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(
                onClick = { onDeny(item) },
                border = BorderStroke(1.dp, PRIMARY_TEXT),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(25.dp)
                    .width(66.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(Color.White)
                    .absolutePadding(0.dp, 0.dp, 0.dp, 0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "거절",
                    color = PRIMARY_TEXT,
                    style = MaterialTheme.typography.subtitle1,
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
            color = PRIMARY_TEXT,
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
