package com.example.pipi.presentation.setting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.Components.DrawStep
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.setProjectTheme
import com.example.pipi.global.constants.utils.passwordErrorMessage

@ExperimentalAnimationApi
@Composable
fun ReSetPasswordScreen(
    navigate: () -> Unit,
    viewModel: ResetUserPasswordViewModel,
    step: Pair<Int, Int> = Pair(3, 1),
    goBack: () -> Unit
) {
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    setProjectTheme {
        Scaffold(topBar = {
            DefaultTopAppbar(title = {
                Text(
                    text = "비밀번호 재설정",
                    style = MaterialTheme.typography.subtitle1,
                    color = Colors.PRIMARY_TEXT,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }, navComponent = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                    contentDescription = "뒤로가기",
                    modifier = Modifier.clickable { goBack() }
                )
            })
        }) {
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
                    DrawStep(step.first, step.second)
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "휴대전화 번호 인증을 해주세요.",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    TextFieldWithErrorMessage(
                        value = password.value,
                        onValueChange = { input -> viewModel.password.value = input },
                        placeholder = "비밀번호 (6자 - 12자) 를 입력해 주세요",
                        errorMessage = passwordErrorMessage(password.value),
                        rightComponent = {
                            if (viewModel.checkPasswordValid()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                    contentDescription = "success",
                                    tint = Color.Unspecified
                                )
                            } else {
                            }
                        },
                        hideInputData = true,
                        title = "비밀번호"
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    TextFieldWithErrorMessage(
                        value = confirmPassword.value,
                        onValueChange = { input -> viewModel.confirmPassword.value = input },
                        placeholder = "비밀번호를 다시 입력해 주세요",
                        errorMessage = if (viewModel.checkConfirmPassword()) "" else "비밀번호가 일치하지 않습니다.",
                        rightComponent = {
                            if (viewModel.checkConfirmPassword()) Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                contentDescription = "success",
                                tint = Color.Unspecified
                            )

                        },
                        hideInputData = true,
                        title = "비밀번호 확인"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    Components.drawDefaultButton(
                        color = if (viewModel.checkConfirmPassword()) Colors.PRIMARY_BRAND else Colors.SECONDARY_TEXT_GHOST,
                        text = "다음",
                        onClick = { viewModel.requestSetNewPassword() },
                        isEnabled = viewModel.checkConfirmPassword()
                    )
                }
            }

        }
    }

    if (viewModel.isResetPasswordSuccess.value) navigate()
}
