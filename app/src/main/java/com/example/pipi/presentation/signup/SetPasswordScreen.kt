package com.example.pipi.presentation.signup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.Components.InputTextField
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun SetPasswordScreen(
    navController: NavController,
    viewModel: SignupViewModel,
    goToMainActivity: () -> Unit
) {
    val password: String by viewModel.password.observeAsState("")
    val confirmPassword: String by viewModel.confirmPassword.observeAsState("")
    val signUpSuccess: Boolean by viewModel.signUpSuccess.observeAsState(false)
    if (signUpSuccess) goToMainActivity()
    setProjectTheme {
        Scaffold(topBar = {
            DefaultTopAppbar(title = {
                Text(
                    text = "회원가입",
                    style = MaterialTheme.typography.subtitle2
                )
            }, navComponent = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    modifier = Modifier.clickable { navController.navigateUp() },
                    contentDescription = "뒤로가기"
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
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_step_3),
                        contentDescription = "step1",
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "비밀번호를 설정해 주세요.",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    TextFieldWithErrorMessage(
                        value = password,
                        onValueChange = { input -> viewModel.password.value = input },
                        placeholder = "비밀번호 (6자 - 12자) 를 입력해 주세요",
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
                    TextFieldWithErrorMessage(
                        value = confirmPassword,
                        onValueChange = { input -> viewModel.confirmPassword.value = input },
                        placeholder = "비밀번호를 다시 입력해 주세요",
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
                        color = if (viewModel.checkConfirmPassword()) Colors.PRIMARY_BRAND else Colors.SECONDARY_TEXT_GHOST,
                        text = "다음",
                        onClick = {
                            viewModel.requestSignUp()
                        },
                        isEnabled = viewModel.checkConfirmPassword()
                    )
                }
            }
        }
    }
}
