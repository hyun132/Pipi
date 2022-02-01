package com.example.pipi.presentation.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.ALERT
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.presentation.main.MainViewModel
import com.example.pipi.presentation.main.Member


@Composable
fun UserInfoScreen(
    viewModel: MainViewModel,
    member: Member = Member("테스터", R.drawable.ic_launcher_foreground, null),
    goBack: () -> Unit,
    goToMakeScheduleActivity: (Member) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // first, set the max size
            .verticalScroll(rememberScrollState()) // then set the scroll
    )
    {
        DrawTopAppBar(
            text = "회원 정보", R.drawable.ic_back
        ) { goBack() }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = member.profileImage),
                contentDescription = "프로필 이미지",
                Modifier
                    .size(119.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Text(
                text = "${member.nickname} 회원님",
                style = MaterialTheme.typography.h1,
                color = PRIMARY_TEXT
            )
        }

        Text(text = "인바디", style = MaterialTheme.typography.body1)
        Text(text = "임시버튼", modifier = Modifier.clickable { goToMakeScheduleActivity(member) })



        member.inbody?.let {
            Image(
                imageVector = ImageVector.vectorResource(id = it),
                contentDescription = "인바디",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(287.dp)
            )
        } ?: run {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(287.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_inbody),
                    contentDescription = "인바디"
                )
                Text(text = "회원님께서 아직\n인바디 사진을 업로드하지 않았어요!", style = MaterialTheme.typography.body2)
            }
        }



        Text(text = "운동 목표", style = MaterialTheme.typography.body1)
        Text(
            text = "회원님께서 아직 운동목표를 설정하지 않았어요!",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .padding(18.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DrawTopAppBar(text: String, navIcon: Int, goBack: () -> Unit) {
    Components.DefaultTopAppbar(title = {
        Text(
            text = text,
            style = MaterialTheme.typography.h1,
            color = Colors.PRIMARY_TEXT,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }, navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = navIcon),
            contentDescription = "닫기",
            Modifier
                .size(12.dp)
                .clickable { goBack() }
        )
    })
}

@Composable
fun DrawModal(
    content: @Composable () -> Unit,
    onNagativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Column {
        val openDialog = remember { mutableStateOf(false) }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(text = "Dialog Title")
                },
                text = {
                    Text("해당 회원을 삭제하시겠습니까?", style = MaterialTheme.typography.h4, fontSize = 16.sp)
                },
                confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("삭제", color = ALERT)
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("취소")
                    }
                }
            )
        }
    }

}
