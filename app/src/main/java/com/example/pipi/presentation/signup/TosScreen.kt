package com.example.pipi.presentation.signup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.presentation.login.labeledCheckbox
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BRAND
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun TosScreen(
    navController: NavController,
    viewModel: SignupViewModel,
    backToMain: () -> Unit
) {
    var agreeAll by remember { mutableStateOf(false) }

    setProjectTheme {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") { backToMain() } }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (contentx, button, snackbar) = createRefs()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(contentx) { top.linkTo(parent.top) },
                ) {
                    labeledCheckbox(
                        label = "전체동의",
                        onChecked = { ischecked -> agreeAll = ischecked },
                        checked = agreeAll
                    )
//                    labeledCheckbox(
//                        label = "항목1",
//                        onChecked = {  },
//                        checked = agreeAll
//                    )
                    Spacer(modifier = Modifier.height(28.dp))
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (true) PRIMARY_BRAND else SECONDARY_TEXT_GHOST,
                        text = "다음",
                        onClick = { navController.navigate("nickName") },
                        isEnabled = true
                    )
                }
            }
        }
    }
}
//
//@Composable
//fun showSnackbar(showDialog: MutableState<Boolean>) {
//    //스낵바는 따로 함수로 빼고, base에 넣을지 고민해볼것.
//    if (showDialog.value == true) {
//        Snackbar(
//            action = {
//                TextButton(
//                    content = {
//                        Text(text = "확인", color = MAIN_PURPLE)
//                    },
//                    onClick = { Timber.d("clicked") }
//                )
//            },
//            backgroundColor = DARK_GRAY
//        ) {
//            Text(
//                text = "인증번호를 문자로 전송하였습니다.",
//                style = MaterialTheme.typography.subtitle1,
//                fontSize = 11.sp,
//                color = Color.White
//            )
//        }
//    }
//}
