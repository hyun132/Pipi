package com.example.pipi.feature_login.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.feature_login.presentation.login.drawPwInput
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@Composable
fun SetPasswordScreen(
    navController: NavController,
    viewModel: SignupViewModel
) {
    setProjectTheme(content = {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") }) {
            ConstraintLayout() {
                val (step, input,button) = createRefs()
                Box(
                    Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .constrainAs(step) {
                            top.linkTo(parent.bottom)
                        }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        //임시로 이미지 넣어둠.
                        Image(
                            painter = painterResource(id = R.drawable.ic_small_logo),
                            contentDescription = "signup step",
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "휴대전화 번호 인증을 해주세요",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2
                        )
                    }

                }

                Box(
                    Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .constrainAs(input) {
                            top.linkTo(step.bottom)
                        }) {

                    drawPwInput(
                        password = viewModel.password.value,
                        onChanged = {},
                        onCancelClicked = {
                            viewModel.password.value = ""

                        })
                }

                Box(
                    Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .constrainAs(button) {
                            top.linkTo(step.bottom)
                        }) {
                    Components.drawDefaultButton(
                        color = MaterialTheme.colors.primary,
                        text = "다음",
                        onClick = {  },
                        isEnabled = true
                    )
                }

            }
        }
    })
}