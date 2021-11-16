package com.example.pipi.presentation.setting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.InputTextField
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun ReSetPasswordScreen(
    navController: NavController,
    viewModel: ResetUserInfoViewModel
) {
    val password: String by viewModel.password.observeAsState("")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState("")
    setProjectTheme {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") { navController.navigateUp() } }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (contentx, button) = createRefs()
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(contentx) { top.linkTo(parent.top) }) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_step_3),
                        contentDescription = "step1",
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "휴대전화 번호 인증을 해주세요.",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    InputTextField(
                        input = password,
                        onChanged = { input -> viewModel.password.value = input },
                        hint = "비밀번호 (6자 - 12자) 를 입력해 주세요",
                        errorMessage = if (viewModel.checkPasswordValid()) "" else "비밀번호는 6자-12자로 설정해 주세요",
                        rightComponent = {
                            if (viewModel.checkPasswordValid()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                    contentDescription = "success"
                                )
                            } else {
                            }
                        },
                        hideInputData = true,
                        title = "비밀번호"
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    InputTextField(
                        input = confirmPassword,
                        onChanged = { input -> viewModel.confirmPassword.value = input },
                        hint = "비밀번호를 다시 입력해 주세요",
                        errorMessage = if (viewModel.checkConfirmPassword()) "" else "비밀번호가 일치하지 않습니다.",
                        rightComponent = {
                            if (viewModel.checkConfirmPassword()) Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                contentDescription = "success"
                            ) else {
                            }

                        },
                        hideInputData = true,
                        title = "비밀번호 확인"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    Components.drawDefaultButton(
                        color = if (viewModel.checkConfirmPassword()) Colors.MAIN_PURPLE else Colors.GRAY2,
                        text = "다음",
                        onClick = { /* 여기서 인증 성공했는지 체크하고 네비게이션 해야함*/ },
                        isEnabled = viewModel.checkConfirmPassword()
                    )
                }
            }

        }
    }
}
