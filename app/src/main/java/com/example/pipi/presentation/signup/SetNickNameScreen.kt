package com.example.pipi.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalComposeUiApi
@Composable
fun SetNickNameScreen(
    navController: NavController,
    viewModel: SignupViewModel
) {
    val nickName: String by viewModel.nickName.observeAsState("")
    setProjectTheme {
        Scaffold(topBar = {
            Components.drawTextTitleTopAppbar(
                "회원가입"
            ) { navController.navigateUp() }
        }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (content, button) = createRefs()
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(content) { top.linkTo(parent.top) }) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_step_2),
                        contentDescription = "step2",
                        alignment = Alignment.Center,
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "닉네임을 설정해 주세요.", style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    TextFieldWithErrorMessage(
                        value = nickName,
                        onValueChange = { viewModel.nickName.value = it },
                        placeholder = "닉네임 (6자 - 12자) 을 입력해 주세요",
                        // 에러메시지 여러가지일때 어떻게 처리할지 생각해볼 것! 컴포넌트 내에 state로 둘지.. viewModel에 둘지..?
                        errorMessage = "닉네임 (6자 - 12자) 을 입력해 주세요",
                        rightComponent = {
                            if (viewModel.checkNickNameValid()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                    contentDescription = "success"
                                )
                            } else {
                            }
                        },
                        hideInputData = false,
                        title = "닉네임"
                    )
//                    Components.drawDefaultButton(
//                        color = if (viewModel.checkNickNameValid()) Colors.MAIN_PURPLE else Colors.GRAY2,
//                        text = "다음",
//                        onClick = { navController.navigate("setPassword") },
//                        isEnabled = viewModel.checkNickNameValid()
//                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    Components.drawDefaultButton(
                        color = if (viewModel.checkNickNameValid()) Colors.PRIMARY_BRAND else Colors.SECONDARY_TEXT_GHOST,
                        text = "다음",
                        onClick = { navController.navigate("setPassword") },
                        isEnabled = viewModel.checkNickNameValid()
                    )
                }
            }
        }
    }
}